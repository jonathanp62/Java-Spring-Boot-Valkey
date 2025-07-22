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
        final String clientName = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(clientName));
        }

        return clientName;
    }

    /// Get the client identifier.
    ///
    /// @return long
    public long clientId() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<Long> future = this.glideClient.clientId();
        final Long clientId = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(clientId));
        }

        return clientId;
    }

    /// Get the client information.
    ///
    /// @return java.lang.String
    public String info() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<String> future = this.glideClient.info();
        final String info = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(info));
        }

        return info;
    }

    /// Ping.
    ///
    /// @return java.lang.String
    public String ping() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<String> future = this.glideClient.ping();
        final String ping = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(ping));
        }

        return ping;
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
        final GlideString ping = future.join();
        final String result = ping.getString();

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
        final GlideString echo = future.join();
        final String result = echo.getString();

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
        final String flushall = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(flushall));
        }

        return flushall;
    }

    /// Get the database size.
    ///
    /// @return long
    public long dbsize() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<Long> future = this.glideClient.dbsize();
        final long dbsize = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(dbsize));
        }

        return dbsize;
    }

}
