package net.jmp.spring.boot.valkey.sail;

/*
 * (#)Sail.java 0.5.0   08/04/2025
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

import java.util.concurrent.ExecutionException;

import net.jmp.spring.boot.valkey.ezglide.EZGlide;

/// The Sail class.
///
/// @version    0.5.0
/// @since      0.5.0
public final class Sail implements AutoCloseable {
    /// The Sail configuration.
    private final SailConfig sailConfig;

    /// The EZGlide instance.
    private final EZGlide ezGlide;

    /// The Glide client.
    private final GlideClient glideClient;

    /// The constructor.
    ///
    /// @param  sailConfig  net.jmp.spring.boot.valkey.sail.SailConfig
    public Sail(final SailConfig sailConfig) {
        this.sailConfig = sailConfig;

        this.glideClient = this.connect();
        this.ezGlide = new EZGlide(this.glideClient);
    }

    /// Create a new bucket instance.
    ///
    /// @param  name    java.lang.String
    /// @return         net.jmp.spring.boot.valkey.sail.SBucket
    public SBucket newBucket(final String name) {
        return new SBucket(this.ezGlide, name);
    }

    /// Create a new server instance.
    ///
    /// @return net.jmp.spring.boot.valkey.sail.SServer
    public SServer newServer() {
        return new SServer(this.ezGlide);
    }

    /// Create a new client instance.
    ///
    /// @return net.jmp.spring.boot.valkey.sail.SClient
    public SClient newClient() {
        return new SClient(this.ezGlide);
    }

    /// Connect to Valkey using Glide.
    ///
    /// @return glide.api.GlideClient
    /// @throws java.lang.RuntimeException  When the Glide client cannot be created
    private GlideClient connect() {
        final GlideClientConfiguration config =
                GlideClientConfiguration.builder()
                        .address(NodeAddress.builder()
                                .host(this.sailConfig.getHostName())
                                .port(this.sailConfig.getPort())
                                .build()
                        )
                        .clientName(this.sailConfig.getClientName())
                        .useTLS(this.sailConfig.isUseSSL())
                        .build();

        GlideClient client = null;

        try {
            client = GlideClient.createClient(config).exceptionally(throwable -> {
                System.err.println("Glide client creation incurred an exception: " + throwable.getMessage());
                return null;
            }).get();
        } catch (final ExecutionException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            } else {
                System.err.println("Glide client creation incurred an execution exception: " + e.getMessage());
            }
        }

        if (client == null) {
            throw new RuntimeException("Unable to create Glide client");
        }

        return client;
    }

    /// Close the connection to Valkey.
    @Override
    public void close() {
        if (this.glideClient != null) {
            try {
                this.glideClient.close();
            } catch (final ExecutionException ee) {
                System.err.println("Glide client close incurred an execution exception: " + ee.getMessage());
            }
        }
    }
}
