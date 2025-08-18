package net.jmp.spring.boot.valkey.sail;

/*
 * (#)SString.java  0.5.0   08/04/2025
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

import java.util.Optional;

import net.jmp.spring.boot.valkey.ezglide.EZGlide;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The Sail string class.
///
/// @version    0.5.0
/// @since      0.5.0
public final class SString extends SObject {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The constructor.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    /// @param  name    java.lang.String
    SString(final EZGlide ezGlide, final String name) {
        super(ezGlide, name);
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

    /// Set the value if absent. True is returned if the value was set.
    ///
    /// @param  value   java.lang.String
    /// @return         boolean
    public boolean setIfAbsent(final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(value));
        }

        boolean result;

        final Optional<String> currentValue = this.get();

        if (currentValue.isPresent()) {
            result = false;
        } else {
            this.ezGlide.set(this.name, value);

            result = true;
        }

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

    /// Append the specified value to the key.
    ///
    /// @param  value   java.lang.String
    /// @return         long
    public long append(final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(value));
        }

        final long result = this.ezGlide.append(this.name, value);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the length of the value.
    ///
    /// @return long
    public long length() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final long result = this.ezGlide.strlen(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get the value then delete the key.
    ///
    /// @return java.util.Optional<java.lang.String>
    public Optional<String> getThenDelete() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final String result = this.ezGlide.getdel(this.name).orElse(null);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Set the value at the specified offset.
    /// The length of the new string is returned.
    ///
    /// @param  start   int
    /// @param  value   java.lang.String
    /// @return         long
    public long setRange(final int start, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(start, value));
        }

        final long result = this.ezGlide.setrange(this.name, start, value);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the substring of the value.
    ///
    /// @param  start int
    /// @param  end   int
    /// @return       java.lang.String
    public String getRange(final int start, final int end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(start, end));
        }

        final String result = this.ezGlide.getrange(this.name, start, end);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the substring of the value.
    ///
    /// @param  start int
    /// @param  end   int
    /// @return       java.lang.String
    public String substr(final int start, final int end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(start, end));
        }

        final String result = this.getRange(start, end);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Copy the string at this key to a new target string.
    ///
    /// @param  target  java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.boot.valkey.sail.SString>
    @Override
    public Optional<SString> copy(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        SString result = null;

        if (super.copyObject(target)) {
            result = new SString(this.ezGlide, target);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Move the string at this key to a new target string.
    ///
    /// @param  target  java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.boot.valkey.sail.SString>
    @Override
    public Optional<SString> move(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        SString result = null;

        if (super.moveObject(target)) {
            result = new SString(this.ezGlide, target);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }
}
