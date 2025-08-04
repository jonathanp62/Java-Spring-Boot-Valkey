package net.jmp.spring.boot.valkey;

/*
 * (#)SailService.java  0.5.0   08/04/2025
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

import net.jmp.spring.boot.valkey.sail.Sail;
import net.jmp.spring.boot.valkey.sail.SailConfig;

import static net.jmp.util.logging.LoggerUtils.entry;
import static net.jmp.util.logging.LoggerUtils.exit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

/// The Sail service class.
///
/// @version    0.5.0
/// @since      0.5.0
@Service
public class SailService {
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
    @Value("${glide.flushDbOnSailServiceStart}")
    private boolean flushDbOnServiceStart;

    /// Flush the database at the end when true.
    @Value("${glide.flushDbOnSailServiceStop}")
    private boolean flushDbOnServiceStop;

    /// The default constructor.
    public SailService() {
        super();
    }

    /// The demo method.
    public void demo() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final SailConfig sailConfig = SailConfig.builder()
            .clientName(this.glideClientName)
            .hostName(this.glideHost)
            .port(this.glidePort)
            .useSSL(this.glideUseSsl)
            .build();

        this.logger.info("Sail config: {}", sailConfig);

        try (final Sail sail = new Sail(sailConfig)) {

        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
