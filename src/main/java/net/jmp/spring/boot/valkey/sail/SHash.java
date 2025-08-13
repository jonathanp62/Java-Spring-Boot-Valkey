package net.jmp.spring.boot.valkey.sail;

/*
 * (#)SHash.java    0.5.0   08/11/2025
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.jmp.spring.boot.valkey.ezglide.EZGlide;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The Sail hash class.
///
/// @version    0.5.0
/// @since      0.5.0
public final class SHash extends SObject {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The constructor.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    /// @param  name    java.lang.String
    SHash(final EZGlide ezGlide, final String name) {
        super(ezGlide, name);
    }

    /// Set a hash as the value associated with the key.
    ///
    /// @param  map java.util.Map<java.lang.String, java.lang.String>
    /// @return     long
    public long set(final Map<String, String> map) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(map));
        }

        final long result = this.ezGlide.hset(this.name, map);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return a list of keys in the hash.
    ///
    /// @return java.util.List<java.lang.String>
    public List<String> keys() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<String> result = this.ezGlide.hkeys(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return a list of values in the hash.
    ///
    /// @return java.util.List<java.lang.String>
    public List<String> values() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<String> result = this.ezGlide.hvals(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the number of entries in the hash.
    ///
    /// @return long
    public long len() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final long result = this.ezGlide.hlen(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Copy the hash at this key to a new target hash.
    ///
    /// @param  target  java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.boot.valkey.sail.SHash>
    @Override
    public Optional<SHash> copy(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        SHash result = null;

        if (super.copyObject(target)) {
            result = new SHash(this.ezGlide, target);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Move the hash at this key to a new target hash.
    ///
    /// @param  target  java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.boot.valkey.sail.SHash>
    @Override
    public Optional<SHash> move(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        SHash result = null;

        if (super.moveObject(target)) {
            result = new SHash(this.ezGlide, target);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }
}
