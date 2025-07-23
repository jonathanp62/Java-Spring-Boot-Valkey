package net.jmp.spring.boot.valkey.ezglide;

/*
 * (#)EZGlide.java  0.4.0   07/22/2025
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

import glide.api.models.GlideString;

import static glide.api.models.GlideString.gs;

import java.util.List;
import java.util.Optional;

import java.util.concurrent.CompletableFuture;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The EZ Glide class.
///
/// @version    0.4.0
/// @since      0.4.0
public final class EZGlide {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The Glide client.
    private final GlideClient glideClient;

    /// The constructor.
    ///
    /// @param  glideClient  glide.api.GlideClient
    public EZGlide(final GlideClient glideClient) {
        super();

        this.glideClient = glideClient;
    }

    /// Get the client name.
    ///
    /// @return java.lang.String
    public String clientGetName() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<String> future = this.glideClient.clientGetName();
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get the client identifier.
    ///
    /// @return long
    public long clientId() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<Long> future = this.glideClient.clientId();
        final Long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get the client information.
    ///
    /// @return java.lang.String
    public String info() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<String> future = this.glideClient.info();
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Ping.
    ///
    /// @return java.lang.String
    public String ping() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<String> future = this.glideClient.ping();
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Ping with a message.
    ///
    /// @param  message java.lang.String
    /// @return         java.lang.String
    public String ping(final String message) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(message));
        }

        final CompletableFuture<GlideString> future = this.glideClient.ping(gs(message));
        final GlideString value = future.join();
        final String result = value.getString();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Echo with a message.
    ///
    /// @param  message java.lang.String
    /// @return         java.lang.String
    public String echo(final String message) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(message));
        }

        final CompletableFuture<GlideString> future = this.glideClient.echo(gs(message));
        final GlideString value = future.join();
        final String result = value.getString();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Flush the database.
    ///
    /// @return java.lang.String
    public String flushall() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<String> future = this.glideClient.flushall();
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get the database size.
    ///
    /// @return long
    public long dbsize() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<Long> future = this.glideClient.dbsize();
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Set a key with a value.
    ///
    /// @param  key     java.lang.String
    /// @param  value   java.lang.String
    /// @return         java.lang.String
    public String set(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final CompletableFuture<String> future = this.glideClient.set(gs(key), gs(value));
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get a value from the specified key.
    ///
    /// @param  key java.lang.String
    /// @return     java.util.Optional<java.lang.String>
    public Optional<String> get(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        String result = null;

        final CompletableFuture<GlideString> future = this.glideClient.get(gs(key));
        final GlideString value = future.join();

        if (value != null) {
            result = value.getString();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Append the specified value to a key
    /// and return the new length of the value.
    ///
    /// @param  key     java.lang.String
    /// @param  value   java.lang.String
    /// @return         long
    public long append(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final CompletableFuture<Long> future = this.glideClient.append(gs(key), gs(value));
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Copy the value in the source key to the target key.
    ///
    /// @param  source  java.lang.String
    /// @param  target  java.lang.String
    /// @return         boolean
    public boolean copy(final String source, final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(source, target));
        }

        final CompletableFuture<Boolean> future = this.glideClient.copy(gs(source), gs(target));
        final boolean result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return true if the key exists and false if not.
    ///
    /// @param  key java.lang.String
    /// @return     boolean
    public boolean exists(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final GlideString[] keys = new GlideString[] { gs(key) };
        final CompletableFuture<Long> future = this.glideClient.exists(keys);
        final long value = future.join();
        final boolean result = value == 1;

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// From the list of specified keys, return the number of keys that exist.
    ///
    /// @param  keys    java.util.List<java.lang.String>
    /// @return         long
    public long exists(final List<String> keys) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(keys));
        }

        long result = 0;

        if (!keys.isEmpty()) {
            final GlideString[] glideStringKeys = new GlideString[keys.size()];

            for (int i = 0; i < keys.size(); i++) {
                glideStringKeys[i] = gs(keys.get(i));
            }

            final CompletableFuture<Long> future = this.glideClient.exists(glideStringKeys);

            result = future.join();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }
}
