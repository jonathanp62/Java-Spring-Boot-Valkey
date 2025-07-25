package net.jmp.spring.boot.valkey;

/*
 * (#)EZGlideService.java   0.4.0   07/22/2025
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

import glide.api.models.configuration.GlideClientConfiguration;
import glide.api.models.configuration.NodeAddress;

import java.util.List;
import java.util.Map;

import java.util.concurrent.ExecutionException;

import net.jmp.spring.boot.valkey.ezglide.EZGlide;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

/// The EZGlide service class.
///
/// @version    0.4.0
/// @since      0.4.0
@Service
public class EZGlideService {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The Glide client name.
    @Value("${glide.client.name}")
    private String glideClientName;

    /// The Glide host.
    @Value("${glide.host}")
    private String glideHost;

    /// The Glide port.
    @Value("${glide.port}")
    private int glidePort;

    /// True when using SSL with Glide.
    @Value("${glide.useSsl}")
    private boolean glideUseSsl;

    /// Flush the database at the start when true.
    @Value("${glide.flushDbOnEZGlideServiceStart}")
    private boolean flushDbOnServiceStart;

    /// Flush the database at the end when true.
    @Value("${glide.flushDbOnEZGlideServiceStop}")
    private boolean flushDbOnServiceStop;

    /// True when the JSON data type is supported.
    @Value("${valkey.json.supported}")
    private boolean valkeyJsonSupported;

    /// The default constructor.
    public EZGlideService() {
        super();
    }

    /// The demo method.
    public void demo() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        try (final GlideClient glideClient = this.connect()) {
            final EZGlide ezGlide = new EZGlide(glideClient);

            if (this.flushDbOnServiceStart) {
                this.cleanup(ezGlide);
            }

            this.miscellaneous(ezGlide);
            this.getAndSet(ezGlide);
            this.getAndDelete(ezGlide);
            this.hash(ezGlide);
            this.list(ezGlide);

            if (this.flushDbOnServiceStop) {
                this.cleanup(ezGlide);
            }
        } catch (final ExecutionException e) {
            this.logger.error("Glide execution execution: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Miscellaneous commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void miscellaneous(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Client name: {}", ezGlide.clientGetName());
            this.logger.info("Client ID: {}", ezGlide.clientId());
            this.logger.info("Client info: {}", ezGlide.info());
            this.logger.info("Ping: {}", ezGlide.ping());
            this.logger.info("Ping: {}", ezGlide.ping("Pinging..."));
            this.logger.info("Echo: {}", ezGlide.ping("Message that is echoed"));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Get and set commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void getAndSet(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Set: {}", ezGlide.set("Apples", "Oranges"));
            this.logger.info("Get: {}", ezGlide.get("Apples").orElse("Apples not found"));
            this.logger.info("Get: {}", ezGlide.get("Oranges").orElse("Oranges not found"));
            this.logger.info("Append: {}", ezGlide.append("Apples", " and Raisins"));
            this.logger.info("Get: {}", ezGlide.get("Apples").orElse("Apples not found"));
            this.logger.info("Copy: {}", ezGlide.copy("Apples", "Oranges"));
            this.logger.info("Get: {}", ezGlide.get("Oranges").orElse("Oranges not found"));
            this.logger.info("Exists: {}", ezGlide.exists("Oranges"));
            this.logger.info("Exists: {}", ezGlide.exists("Lemons"));
            this.logger.info("Exists: {}", ezGlide.exists(List.of("Oranges", "Lemons", "Apples")));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Get and delete commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void getAndDelete(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Set: {}", ezGlide.set("Name", "Jonathan"));
            this.logger.info("Get: {}", ezGlide.get("Name").orElse("Name not found"));
            this.logger.info("Rename: {}", ezGlide.rename("Name", "My-Name"));
            this.logger.info("Exists: {}", ezGlide.exists("Name"));
            this.logger.info("GetDel: {}", ezGlide.getdel("My-Name"));
            this.logger.info("Exists: {}", ezGlide.exists("My-Name"));
            this.logger.info("Set: {}", ezGlide.set("Middle-Name", "Martin"));
            this.logger.info("Del: {}", ezGlide.del("Middle-Name"));
            this.logger.info("Set: {}", ezGlide.set("Last-Name", "Parker"));
            this.logger.info("Set: {}", ezGlide.set("Middle-Name", "Martin"));
            this.logger.info("Del: {}", ezGlide.del(List.of("Last-Name", "Middle-Name")));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Hash commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void hash(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        final Map<String, String> map = Map.of(
                "firstName", "Wendy",
                "middleName", "Carol",
                "lastName", "Burkins"
        );

        if (this.logger.isInfoEnabled()) {
            this.logger.info("HSet: {}", ezGlide.hset("Names", map));
            this.logger.info("HKeys: {}", ezGlide.hkeys("Names"));
            this.logger.info("HLen: {}", ezGlide.hlen("Names"));
            this.logger.info("HGet: {}", ezGlide.hget("Names", "middleName").orElse("middleName not found in Names"));
            this.logger.info("HGet: {}", ezGlide.hget("Names", "nickName").orElse("nickName not found in Names"));
            this.logger.info("HExists: {}", ezGlide.hexists("Names", "middleName"));
            this.logger.info("HExists: {}", ezGlide.hexists("Names", "nickName"));
            this.logger.info("HDel: {}", ezGlide.hdel("Names", "middleName"));
            this.logger.info("HDel: {}", ezGlide.hdel("Names", List.of("firstName", "lastName")));
            this.logger.info("HSet: {}", ezGlide.hset("Names", map));
            this.logger.info("HGetAll: {}", ezGlide.hgetall("Names"));
            this.logger.info("HGetAll: {}", ezGlide.hgetall("Undefined"));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// List commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void list(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        /*
         * Pushing to the list means that the element is added to the head of the list.
         * So the list will look like this:
         *   Grapes
         *   Strawberries
         *   Blueberries
         *   Cherries
         */

        final List<String> list = List.of("Cherries", "Blueberries", "Strawberries", "Grapes");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("LPush: {}", ezGlide.lpush("Fruits", list));
            this.logger.info("LIndex: {}", ezGlide.lindex("Fruits", 0).orElse("Fruits[0] not found"));
            this.logger.info("LIndex: {}", ezGlide.lindex("Fruits", 1).orElse("Fruits[1] not found"));
            this.logger.info("LSet: {}", ezGlide.lset("Fruits", 0, "Green Grapes"));
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
            this.logger.trace(entryWith());
        }

        final GlideClientConfiguration config =
                GlideClientConfiguration.builder()
                        .address(NodeAddress.builder()
                                .host(this.glideHost)
                                .port(this.glidePort)
                                .build()
                        )
                        .clientName(this.glideClientName)
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
    /// @param  ezGlide  net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void cleanup(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        this.logger.info("Flush All: {}", ezGlide.flushall());
        this.logger.info("DB size: {}", ezGlide.dbsize());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
