package net.jmp.spring.boot.valkey.sail;

/*
 * (#)SBucket.java  0.5.0   08/04/2025
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

import static net.jmp.util.logging.LoggerUtils.*;

import net.jmp.spring.boot.valkey.ezglide.EZGlide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/// The Sail bucket class.
///
/// @version    0.5.0
/// @since      0.5.0
public final class SBucket extends SObject {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The name.
    private final String name;

    /// The constructor.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    /// @param  name    java.lang.String
    public SBucket(final EZGlide ezGlide, final String name) {
        super(ezGlide);

        this.name = name;
    }

    /// Set the value. OK is returned.
    ///
    /// @param  value   java.lang.String
    /// @return         java.lang.String
    public String set(final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(value));
        }

        final String result = this.ezGlide.set(this.name, value);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get the value.
    ///
    /// @return java.util.Optional<java.lang.String>
    public Optional<String> get() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final String result = this.ezGlide.get(this.name).orElse(null);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }
}
