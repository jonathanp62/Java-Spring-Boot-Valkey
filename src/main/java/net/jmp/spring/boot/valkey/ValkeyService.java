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

import glide.api.models.GlideString;

import glide.api.models.commands.LInsertOptions;

import glide.api.models.configuration.GlideClientConfiguration;
import glide.api.models.configuration.NodeAddress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.util.concurrent.CompletableFuture;
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
///  Set
///  Sorted Set
///  Vector Set
///  JSON
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

    /// The default constructor.
    public ValkeyService() {
        super();
    }

    /// The run method.
    public void run() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final GlideClient glideClient = this.connect();

        if (glideClient != null) {
            try {
                final GlideClient client = glideClient; // Lambdas require final variables

                final CompletableFuture<Void> clientName = glideClient.clientGetName()
                        .thenAccept(name -> this.logger.info("CLIENT-NAME: {}", name));

                final CompletableFuture<Void> clientId = glideClient.clientId()
                        .thenAccept(id -> this.logger.info("CLIENT-ID: {}", id));

                final CompletableFuture<Void> info = glideClient.info()
                        .thenAccept(str -> this.logger.info("INFO: {}", str));

                final CompletableFuture<Void> completedFutures = CompletableFuture.allOf(clientName, clientId, info);

                completedFutures.join();
                completedFutures.thenRun(() -> {
                    try {
                        this.echoAndPing(client);
                        this.getAndSet(client);
                        this.getAndDelete(client);
                        this.hash(client);
                        this.list(client);
                    }  catch (final ExecutionException | InterruptedException e) {
                        if (e instanceof InterruptedException) {
                            Thread.currentThread().interrupt();
                            this.logger.error("Glide execution was interrupted: {}", e.getMessage(), e);
                        } else {
                            this.logger.error("Glide execution incurred an exception: {}", e.getMessage(), e);
                        }
                    }

                    client.flushall()
                            .thenAccept(str -> this.logger.info("FLUSH-ALL: {}", str))
                            .join();

                    client.dbsize()
                            .thenAccept(size -> this.logger.info("DB-SIZE: {}", size))
                            .join();
                });
            } finally {
                try {
                    glideClient.close();
                } catch (final Exception e) {
                    this.logger.error("Glide client close failed with an exception: {}", e.getMessage(), e);
                }
            }
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Connect to Valkey using Glide.
    ///
    /// @return glide.api.GlideClient
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

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(glideClient));
        }

        return glideClient;
    }

    /// Echo and ping commands.
    ///
    /// @param  client  glide.api.GlideClient
    /// @throws         java.util.concurrent.ExecutionException When an error occurs
    /// @throws         java.lang.InterruptedException          When interrupted
    private void echoAndPing(final GlideClient client) throws ExecutionException, InterruptedException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        this.logger.info("PING: {}", client.ping(gs("PING")).get());
        this.logger.info("ECHO(back at you): {}", client.echo(gs("back at you")).get());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Get and set commands.
    ///
    /// @param  client  glide.api.GlideClient
    /// @throws         java.util.concurrent.ExecutionException When an error occurs
    /// @throws         java.lang.InterruptedException          When interrupted
    private void getAndSet(final GlideClient client) throws ExecutionException, InterruptedException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final GlideString apples = gs("apples");
        final GlideString oranges = gs("oranges");

        this.logger.info("SET(apples, oranges): {}", client.set(apples, oranges).get());    // Returns OK
        this.logger.info("GET(apples): {}", client.get(apples).get());
        this.logger.info("GET(oranges): {}", client.get(oranges).get());    // Returns null
        this.logger.info("APPEND(apples, and raisins): {}", client.append(apples, gs(" and raisins")).get());   // Returns 19
        this.logger.info("GET(apples): {}", client.get(apples).get());
        this.logger.info("COPY(apples, oranges): {}", client.copy(apples, oranges).get());  // Returns true
        this.logger.info("GET(oranges): {}", client.get(oranges).get());
        this.logger.info("EXISTS(oranges): {}", client.exists(new GlideString[] { oranges }).get());    // Returns 1
        this.logger.info("EXISTS(lemons): {}", client.exists(new GlideString[] { gs("lemons") }).get());    // Returns 0

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Get and delete commands.
    ///
    /// @param  client  glide.api.GlideClient
    /// @throws         java.util.concurrent.ExecutionException When an error occurs
    /// @throws         java.lang.InterruptedException          When interrupted
    private void getAndDelete(final GlideClient client) throws ExecutionException, InterruptedException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final GlideString name = gs("name");
        final GlideString myName = gs("my-name");

        this.logger.info("SET(name, Jonathan): {}", client.set(name, gs("Jonathan")).get());    // Returns OK
        this.logger.info("GET(name): {}", client.get(name).get());
        this.logger.info("RENAME(name, my-name): {}", client.rename(name, myName).get());   // Returns OK
        this.logger.info("GETDEL(my-name): {}", client.getdel(myName).get());   // Returns Jonathan
        this.logger.info("GET(my-name): {}", client.get(myName).get());         // Returns null

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Hash commands.
    ///
    /// @param  client  glide.api.GlideClient
    /// @throws         java.util.concurrent.ExecutionException When an error occurs
    /// @throws         java.lang.InterruptedException          When interrupted
    private void hash(final GlideClient client) throws ExecutionException, InterruptedException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final Map<GlideString, GlideString> map = Map.of(
                gs("firstName"), gs("Jonathan"),
                gs("lastName"), gs("Parker")
        );

        final GlideString myHash = gs("my-hash");

        this.logger.info("HSET(my-hash, map): {}", client.hset(myHash, map).get());    // Returns 2 if not already created, else 0

        final GlideString[] keys = client.hkeys(myHash).get();
        final GlideString lastName = gs("lastName");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("HKEYS(my-hash): {}", Arrays.toString(keys));      // Returns [firstName, lastName]
            this.logger.info("HLEN(my-hash): {}", client.hlen(myHash).get());   // Returns 2
            this.logger.info("HGET(my-hash, firstName): {}", client.hget(myHash, gs("firstName")).get());
            this.logger.info("HGET(my-hash, lastName): {}", client.hget(myHash, lastName).get());
            this.logger.info("HEXISTS(my-hash, lastName): {}", client.hexists(myHash, lastName).get());   // Returns true
            this.logger.info("HDEL(my-hash, lastName): {}", client.hdel(myHash, new GlideString[] { lastName }).get());  // Returns 1
            this.logger.info("HGET(my-hash, lastName): {}", client.hget(myHash, lastName).get());   // Returns null

            Map<GlideString, GlideString> returnedMap = client.hgetall(myHash).get();

            this.logger.info("HGETALL(my-hash): {}", returnedMap);  // Returns {firstName=Jonathan}

            returnedMap.put(gs("spouse"), gs("Dena"));

            client.hset(myHash, returnedMap);

            returnedMap = client.hgetall(myHash).get();

            this.logger.info("HGETALL(my-hash): {}", returnedMap);  // Returns {spouse=Dena, firstName=Jonathan}
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// List commands.
    ///
    /// @param  client  glide.api.GlideClient
    /// @throws         java.util.concurrent.ExecutionException When an error occurs
    /// @throws         java.lang.InterruptedException          When interrupted
    private void list(final GlideClient client) throws ExecutionException, InterruptedException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final GlideString myList = gs("my-List");
        final GlideString thirdElement = gs("Third element");

        final GlideString[] myArray = new GlideString[] { gs("First"), gs("Second"), gs("Third") };

        if (this.logger.isInfoEnabled()) {
            this.logger.info("LPUSH: {}", client.lpush(myList, myArray).get()); // Returns 3
            this.logger.info("LSET: {}", client.lset(myList, 0, gs("First element")).get());    // Returns OK
            this.logger.info("LSET: {}", client.lset(myList, 1, gs("Second element")).get());   // Returns OK
            this.logger.info("LSET: {}", client.lset(myList, 2, thirdElement).get());    // Returns OK
            this.logger.info("LLEN(my-list): {}", client.llen(myList).get());   // Returns 3
            this.logger.info("LINDEX(my-list, 1): {}", client.lindex(myList, 1).get());   // Returns Second element
            this.logger.info("LPOS(my-list, Third element): {}", client.lpos(myList, thirdElement).get());   // Returns 2
            this.logger.info("LINSERT(my-list, AFTER, Third element, Fourth element): {}", client.linsert(myList,
                    LInsertOptions.InsertPosition.AFTER,
                    thirdElement,
                    gs("Fourth element")).get());   // Returns 4

            final GlideString[] rangedItems = client.lrange(myList, 0, 2).get();

            this.logger.info("LRANGE(my-list, 0, 2): {}", Arrays.toString(rangedItems));   // Returns [First element, Second element, Third element]
            this.logger.info("LPOP(my-list): {}", client.lpop(myList).get());   // Returns First element
            this.logger.info("LREM(my-list, Second element): {}", client.lrem(myList, 1, gs("Second element")).get());   // Returns 1

            final List<String> list = new ArrayList<>();
            final long itemCount = client.llen(myList).get();

            for (int i = 0; i < itemCount; i++) {
                list.add(client.lindex(myList, i).get().getString());
            }

            this.logger.info("list: {}", list);   // Returns [Third element, Fourth element]
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

}
