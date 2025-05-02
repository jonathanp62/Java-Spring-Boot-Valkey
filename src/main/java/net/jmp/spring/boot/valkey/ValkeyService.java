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

import glide.api.models.configuration.GlideClientConfiguration;
import glide.api.models.configuration.NodeAddress;

import java.util.Arrays;
import java.util.Map;
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
///  client.copy();
///  client.customCommand();
///  client.exec();
///  client.fcall();
///  client.function...();
///  client.lastsave();
///  client.lolwut();
///
/// Data types to get acquainted with:
///  String
///  Hash
///  List
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

        final GlideClientConfiguration config =
                GlideClientConfiguration.builder()
                        .address(NodeAddress.builder()
                                .host(this.glideHost)
                                .port(this.glidePort)
                                .build()
                        )
                        .useTLS(this.glideUseSsl)
                        .build();

        try (final GlideClient client = GlideClient.createClient(config).get()) {
            this.logger.info("CLIENTGETNAME: {}", client.clientGetName().get());    // Returns null
            this.logger.info("CLIENTID: {}", client.clientId().get());              // Returns 12
            this.logger.info("INFO: {}", client.info().get());

            this.echoAndPing(client);
            this.getAndSet(client);
            this.getAndDelete(client);
            this.hash(client);

            client.flushall();

            this.logger.info("DBSIZE: {}", client.dbsize().get());  // Returns 0
        } catch (final ExecutionException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

            this.logger.error("Glide example failed with an exception: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Echo and ping commands.
    ///
    /// @param  client  glide.api.GlideClient
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
    private void getAndSet(final GlideClient client) throws ExecutionException, InterruptedException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        this.logger.info("SET(apples, oranges): {}", client.set(gs("apples"), gs("oranges")).get());    // Returns OK
        this.logger.info("GET(apples): {}", client.get(gs("apples")).get());
        this.logger.info("GET(oranges): {}", client.get(gs("oranges")).get());  // Returns null

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Get and delete commands.
    ///
    /// @param  client  glide.api.GlideClient
    private void getAndDelete(final GlideClient client) throws ExecutionException, InterruptedException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(client));
        }

        final GlideString name = gs("name");

        this.logger.info("SET(name, Jonathan): {}", client.set(name, gs("Jonathan")).get());    // Returns OK
        this.logger.info("GET(name): {}", client.get(name).get());
        this.logger.info("GETDEL(name): {}", client.getdel(name).get());    // Returns Jonathan
        this.logger.info("GET(name): {}", client.get(name).get());          // Returns null

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Hash commands.
    ///
    /// @param  client  glide.api.GlideClient
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

            this.logger.info("HGETALL(my-hash): {}", returnedMap.toString());    // Returns {firstName=Jonathan}

            returnedMap.put(gs("spouse"), gs("Dena"));

            client.hset(myHash, returnedMap);

            returnedMap = client.hgetall(myHash).get();

            this.logger.info("HGETALL(my-hash): {}", returnedMap.toString());    // Returns {spouse=Dena, firstName=Jonathan}
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
