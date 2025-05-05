package net.jmp.spring.boot.valkey;

/*
 * (#)ValkeyService.java    0.1.0   05/01/2025
 *
 * @author   Jonathan Parker
 *
 * MIT License
 *
 * Copyright (c) 2025 Jonathan M. Parker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import glide.api.GlideClient;

import static glide.api.models.GlideString.gs;

import glide.api.commands.servermodules.Json;

import glide.api.models.GlideString;

import glide.api.models.commands.LInsertOptions;
import glide.api.models.commands.RangeOptions;
import glide.api.models.commands.ScoreFilter;

import glide.api.models.configuration.GlideClientConfiguration;
import glide.api.models.configuration.NodeAddress;

import java.util.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

/// The Valkey service class.
///
/// @version    0.1.0
/// @since      0.1.0
///
/// APIs to get acquainted with:
///  client.customCommand();
///  client.exec();
///  client.fcall();
///  client.function...();
///  client.lastsave();
///
/// Data types to get acquainted with:
///  String ✔️
///  Hash ✔️
///  List ✔️
///  Set ✔️
///  Sorted Set ✔️
///  JSON ❌
///  Serialized Objects (Kryo, JSON, Java)
@Service
public class ValkeyService {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The Glide host.
    @Value("${glide.host}")
    private String glideHost;

    /// The Glide port.
    @Value("${glide.port}")
    private int glidePort;

    /// True when using SSL with Glide.
    @Value("${glide.useSsl}")
    private boolean glideUseSsl;

    /// Flush the database at the end when true.
    @Value("${glide.flushDb}")
    private boolean glideFlushDb;

    /// True when the JSON data type is supported.
    @Value("${valkey.json.supported}")
    private boolean valkeyJsonSupported;

    /// The default constructor.
    public ValkeyService() {
        super();
    }

    /// The demo method.
    public void demo() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        try (final GlideClient glideClient = this.connect()) {
            final CompletableFuture<Void> clientName = glideClient.clientGetName()
                    .thenAccept(name -> this.logger.info("CLIENT-NAME: {}", name));

            final CompletableFuture<Void> clientId = glideClient.clientId()
                    .thenAccept(id -> this.logger.info("CLIENT-ID: {}", id));

            final CompletableFuture<Void> info = glideClient.info()
                    .thenAccept(str -> this.logger.info("INFO: {}", str));

            final CompletableFuture<Void> completedFutures = CompletableFuture.allOf(clientName, clientId, info);

            try {
                completedFutures.join();
            } catch (final CompletionException e) {
                this.logger.error("Glide execution waiting on futures: {}", e.getMessage(), e);
            }

            completedFutures.thenRun(() -> {
                this.echoAndPing(glideClient);
                this.getAndSet(glideClient);
                this.getAndDelete(glideClient);
                this.hash(glideClient);
                this.list(glideClient);
                this.set(glideClient);
                this.sortedSet(glideClient);

                if (this.valkeyJsonSupported) {
                    this.json(glideClient);
                }

                this.cleanup(glideClient);
            });
        } catch (final ExecutionException e) {
            this.logger.error("Glide execution execution: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Connect to Valkey using Glide.
    ///
    /// @return glide.api.GlideClient
    /// @throws java.lang.RuntimeException  When the Glide client cannot be created
    private GlideClient connect() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final GlideClientConfiguration config =
                GlideClientConfiguration.builder()
                        .address(NodeAddress.builder()
                                .host(this.glideHost)
                                .port(this.glidePort)
                                .build()
                        )
                        .useTLS(this.glideUseSsl)
                        .build();

        GlideClient glideClient = null;

        try {
            glideClient = GlideClient.createClient(config).exceptionally(throwable -> {
                this.logger.error("Glide client creation incurred an exception: {}", throwable.getMessage(), throwable);
                return null;
            }).get();
        } catch (final ExecutionException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
                this.logger.error("Glide client creation was interrupted: {}", e.getMessage(), e);
            } else {
                this.logger.error("Glide client creation incurred an execution exception: {}", e.getMessage(), e);
            }
        }

        if (glideClient == null) {
            throw new RuntimeException("Unable to create Glide client");
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(glideClient));
        }

        return glideClient;
    }

    /// Cleanup the database.
    ///
    /// @param  client  glide.api.GlideClient
    private void cleanup(final GlideClient client) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        try {
            if (this.glideFlushDb) {
                client.flushall()
                        .thenAccept(str -> this.logger.info("FLUSH-ALL: {}", str))
                        .join();
            }

            client.dbsize()
                    .thenAccept(size -> this.logger.info("DB-SIZE: {}", size))
                    .join();
        } catch (final CompletionException e) {
            this.logger.error("Glide execution waiting on futures: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Echo and ping commands.
    ///
    /// @param  client  glide.api.GlideClient
    private void echoAndPing(final GlideClient client) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final CompletableFuture<Void> ping = client.ping(gs("PING"))
                .thenAccept(str -> this.logger.info("PING: {}", str));

        final CompletableFuture<Void> echo = client.echo(gs("Back at you"))
                .thenAccept(str -> this.logger.info("ECHO(Back at you): {}", str));

        try {
            CompletableFuture.allOf(ping, echo).join();
        } catch (final CompletionException e) {
            this.logger.error("Glide execution incurred an exception: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Get and set commands.
    ///
    /// @param  client  glide.api.GlideClient
    private void getAndSet(final GlideClient client) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final GlideString apples = gs("apples");
        final GlideString oranges = gs("oranges");

        try {
            client.set(apples, oranges)
                    .thenAccept(str -> this.logger.info("SET(apples, oranges): {}", str))
                    .join();

            client.get(apples)
                    .thenAccept(str -> this.logger.info("GET(apples): {}", str))
                    .join();

            client.get(oranges)
                    .thenAccept(str -> this.logger.info("GET(oranges): {}", str))
                    .join();

            client.append(apples, gs(" and raisins"))
                    .thenAccept(num -> this.logger.info("APPEND(apples, and raisins): {}", num))
                    .join();

            client.get(apples)
                    .thenAccept(str -> this.logger.info("GET(apples): {}", str))
                    .join();

            client.copy(apples, oranges)
                    .thenAccept(bool -> this.logger.info("COPY(apples, oranges): {}", bool))
                    .join();

            client.get(oranges)
                    .thenAccept(str -> this.logger.info("GET(oranges): {}", str))
                    .join();

            client.exists(new GlideString[] { oranges })
                    .thenAccept(num -> this.logger.info("EXISTS(oranges): {}", num))
                    .join();

            client.exists(new GlideString[] { gs("lemons") })
                    .thenAccept(num -> this.logger.info("EXISTS(lemons): {}", num))
                    .join();
        } catch (final CompletionException e) {
            this.logger.error("Glide exception handling keys: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Get and delete commands.
    ///
    /// @param  client  glide.api.GlideClient
    private void getAndDelete(final GlideClient client) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final GlideString name = gs("name");
        final GlideString myName = gs("my-name");

        try {
            client.set(name, gs("Jonathan"))
                    .thenAccept(str -> this.logger.info("SET(name, Jonathan): {}", str))
                    .join();

            client.get(name)
                    .thenAccept(str -> this.logger.info("GET(name): {}", str))
                    .join();

            client.rename(name, myName)
                    .thenAccept(str -> this.logger.info("RENAME(name, my-name): {}", str))
                    .join();

            client.getdel(myName)
                    .thenAccept(str -> this.logger.info("GETDEL(my-name): {}", str))
                    .join();

            client.get(myName)
                    .thenAccept(str -> this.logger.info("GET(my-name): {}", str))
                    .join();
        } catch (final CompletionException e) {
            this.logger.error("Glide exception handling keys: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Hash commands.
    ///
    /// @param  client  glide.api.GlideClient
    private void hash(final GlideClient client) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final Map<GlideString, GlideString> map = Map.of(
                gs("firstName"), gs("Jonathan"),
                gs("lastName"), gs("Parker")
        );

        final GlideString myHash = gs("my-hash");

        try {
            client.hset(myHash, map)
                    .thenAccept(num -> this.logger.info("HSET(my-hash, map): {}", num))
                    .join();

            final CompletableFuture<GlideString[]> futureKeys = client.hkeys(myHash);
            final GlideString[] keys = futureKeys.join();
            final GlideString lastName = gs("lastName");

            if (this.logger.isInfoEnabled()) {
                this.logger.info("HKEYS(my-hash): {}", Arrays.toString(keys));      // Returns [firstName, lastName]
            }

            client.hlen(myHash)
                    .thenAccept(num -> this.logger.info("HLEN(my-hash): {}", num))
                    .join();

            client.hget(myHash, gs("firstName"))
                    .thenAccept(str -> this.logger.info("HGET(my-hash, firstName): {}", str))
                    .join();

            client.hget(myHash, lastName)
                    .thenAccept(str -> this.logger.info("HGET(my-hash, lastName): {}", str))
                    .join();

            client.hexists(myHash, lastName)
                    .thenAccept(bool -> this.logger.info("HEXISTS(my-hash, lastName): {}", bool))
                    .join();

            client.hdel(myHash, new GlideString[]{ lastName })
                    .thenAccept(num -> this.logger.info("HDEL(my-hash, lastName): {}", num))
                    .join();

            client.hget(myHash, lastName)
                    .thenAccept(str -> this.logger.info("HGET(my-hash, lastName): {}", str))
                    .join();

            final CompletableFuture<Map<GlideString, GlideString>> futureGetAll = client.hgetall(myHash);

            Map<GlideString, GlideString> returnedMap = futureGetAll.join();

            this.logger.info("HGETALL(my-hash): {}", returnedMap);

            returnedMap.put(gs("spouse"), gs("Dena"));

            client.hset(myHash, returnedMap)
                    .thenAccept(num -> this.logger.info("HSET(my-hash, returnedMap): {}", num))
                    .join();

            returnedMap = client.hgetall(myHash).join();

            this.logger.info("HGETALL(my-hash): {}", returnedMap);
        } catch (final CompletionException e) {
            this.logger.error("Glide exception handling a hash: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// List commands.
    ///
    /// @param  client  glide.api.GlideClient
    private void list(final GlideClient client) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final GlideString myList = gs("my-List");
        final GlideString thirdElement = gs("Third element");

        final GlideString[] myArray = new GlideString[] { gs("First"), gs("Second"), gs("Third") };

        try {
            client.lpush(myList, myArray)
                    .thenAccept(num -> this.logger.info("LPUSH: {}", num))
                    .join();

            client.lset(myList, 0, gs("First element"))
                    .thenAccept(str -> this.logger.info("LSET: {}", str))
                    .join();

            client.lset(myList, 1, gs("Second element"))
                    .thenAccept(str -> this.logger.info("LSET: {}", str))
                    .join();

            client.lset(myList, 2, thirdElement)
                    .thenAccept(str -> this.logger.info("LSET: {}", str))
                    .join();

            client.llen(myList)
                    .thenAccept(num -> this.logger.info("LLEN(my-list): {}", num))
                    .join();

            client.lindex(myList, 1)
                    .thenAccept(str -> this.logger.info("LINDEX(my-list, 1): {}", str))
                    .join();

            client.lpos(myList, thirdElement)
                    .thenAccept(num -> this.logger.info("LPOS(my-list, Third element): {}", num))
                    .join();

            client.linsert(myList,
                            LInsertOptions.InsertPosition.AFTER,
                            thirdElement,
                            gs("Fourth element")
                    )
                    .thenAccept(num -> this.logger.info("LINSERT(my-list, AFTER, Third element, Fourth element): {}", num))
                    .join();

            final GlideString[] rangedItems = client.lrange(myList, 0, 2).join();

            if (this.logger.isInfoEnabled()) {
                this.logger.info("LRANGE(my-list, 0, 2): {}", Arrays.toString(rangedItems));   // Returns [First element, Second element, Third element]
            }

            client.lpop(myList)
                    .thenAccept(str -> this.logger.info("LPOP(my-list): {}", str))
                    .join();

            client.lrem(myList, 1, gs("Second element"))
                    .thenAccept(num -> this.logger.info("LREM(my-list, Second element): {}", num))
                    .join();

            final List<String> list = new ArrayList<>();
            final long itemCount = client.llen(myList).join();

            for (int i = 0; i < itemCount; i++) {
                list.add(client.lindex(myList, i).join().getString());
            }

            this.logger.info("list: {}", list);   // Returns [Third element, Fourth element]
        } catch (final CompletionException e) {
            this.logger.error("Glide exception handling a list: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Set commands.
    ///
    /// @param  client  glide.api.GlideClient
    private void set(final GlideClient client) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final GlideString mySet = gs("my-set");
        final GlideString aimee = gs("Aimee");
        final GlideString[] myArray = new GlideString[] { gs("Jonathan"), gs("Dena"), aimee };

        try {
            client.sadd(mySet, myArray)
                    .thenAccept(num -> this.logger.info("SADD: {}", num))
                    .join();

            client.scard(mySet)
                    .thenAccept(num -> this.logger.info("SCARD(my-set): {}", num))
                    .join();

            client.sismember(mySet, aimee)
                    .thenAccept(bool -> this.logger.info("SISMEMBER(my-set, aimee): {}", bool))
                    .join();

            client.srem(mySet, new GlideString[] { aimee })
                    .thenAccept(num -> this.logger.info("SREM(my-set, aimee): {}", num))
                    .join();

            client.smembers(mySet)
                    .join()
                    .forEach(str -> this.logger.info("SMEMBERS(my-set): {}", str.getString()));
        } catch (final CompletionException e) {
            this.logger.error("Glide exception handling a set: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Sorted set commands. The
    /// sorting is by score.
    ///
    /// @param  client  glide.api.GlideClient
    private void sortedSet(final GlideClient client) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final GlideString mySortedSet = gs("my-sorted-set");
        final GlideString ccc = gs("CCC");

        final Map<GlideString, Double> map = Map.of(
                gs("ZZZ"), 1.0,
                gs("YYY"), 2.0,
                gs("XXX"), 3.0,
                ccc, 24.0,
                gs("BBB"), 25.0,
                gs("AAA"), 26.0
        );

        try {
            client.zadd(mySortedSet, map)
                    .thenAccept(num -> this.logger.info("ZADD: {}", num))
                    .join();

            client.zcard(mySortedSet)
                    .thenAccept(num -> this.logger.info("ZCARD(my-sorted-set): {}", num))
                    .join();

            client.zscore(mySortedSet, gs("CCC"))
                    .thenAccept(str -> this.logger.info("ZSCORE(my-sorted-set, CCC): {}", str))
                    .join();

            client.zrank(mySortedSet, gs("CCC"))
                    .thenAccept(num -> this.logger.info("ZRANK(my-sorted-set, CCC): {}", num))
                    .join();

            client.zcount(mySortedSet,
                        new RangeOptions.ScoreBoundary(1.0, true),
                        new RangeOptions.ScoreBoundary(3.0, true)
                    )
                    .thenAccept(num -> this.logger.info("ZCOUNT(my-sorted-set, 1.0, 3.0): {}", num))
                    .join();

            final Object[] elements = client.zmpop(new GlideString[] { mySortedSet }, ScoreFilter.MIN)
                    .join();

            if (this.logger.isInfoEnabled()) {
                this.logger.info("ZMPOP(my-sorted-set, MIN): {}", Arrays.toString(elements));
            }

            client.zrem(mySortedSet, new GlideString[] { ccc })
                    .thenAccept(num -> this.logger.info("ZREM(my-sorted-set, CCC): {}", num))
                    .join();

            final GlideString[] range = client.zrange(mySortedSet, new RangeOptions.RangeByIndex(0, 26)).join();

            if (this.logger.isInfoEnabled()) {
                this.logger.info("ZRANGE(my-sorted-set, 0, 26): {}", Arrays.toString(range));
            }
        } catch (final CompletionException e) {
            this.logger.error("Glide exception handling a sorted set: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// JSON commands.
    ///
    /// @param  client  glide.api.GlideClient
    private void json(final GlideClient client) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        Json.set(client,
                    gs("my-json-key"),
                    gs("$"),
                    gs("my-json-value"))
                .thenAccept(num -> this.logger.info("SET(my-json-key, $, my-json-value): {}", num))
                .join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
