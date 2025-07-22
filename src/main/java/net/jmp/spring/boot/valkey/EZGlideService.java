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

import java.util.concurrent.ExecutionException;

import static net.jmp.util.logging.LoggerUtils.*;

import net.jmp.spring.boot.valkey.ezglide.EZGlide;
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

    /// Flush the database at the end when true.
    @Value("${glide.flushDb}")
    private boolean glideFlushDb;

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

            this.miscellaneous(ezGlide);
        } catch (final ExecutionException e) {
            this.logger.error("Glide execution execution: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Miscellaneous commands.
    private void miscellaneous(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Client name: {}", ezGlide.clientGetName());
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
}
