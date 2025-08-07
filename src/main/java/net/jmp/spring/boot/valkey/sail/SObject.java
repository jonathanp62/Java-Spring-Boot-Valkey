package net.jmp.spring.boot.valkey.sail;

/*
 * (#)SObject.java  0.5.0   08/04/2025
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

import net.jmp.spring.boot.valkey.ezglide.EZGlide;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The Sail object base class.
///
/// @version    0.5.0
/// @since      0.5.0
public class SObject {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The EZGlide instance.
    protected final EZGlide ezGlide;

    /// The object name.
    protected String name;

    /// The constructor.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    /// @param  name    java.lang.String
    protected SObject(final EZGlide ezGlide, final String name) {
        super();

        this.ezGlide = ezGlide;
        this.name = name;
    }

    /// Get the name.
    ///
    /// @return java.lang.String
    public String getName() {
        return this.name;
    }

    /// Return true if the key exists.
    ///
    /// @return boolean
    public boolean exists() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final boolean result = this.ezGlide.exists(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Rename the key. The old key name is replaced with
    /// the new key name and the new key name is returned.
    ///
    /// @param  newName java.lang.String
    /// @return         java.lang.String
    public String rename(final String newName) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(newName));
        }

        final String result = this.ezGlide.rename(this.name, newName);

        if ("OK".equals(result)) {
            this.name = newName;
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(this.name));
        }

        return this.name;
    }

    /// Delete the key.
    ///
    /// @return boolean
    public boolean delete() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final boolean result = this.ezGlide.del(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Copy the object at this key to the target key.
    ///
    /// @param  target  java.lang.String
    /// @return         boolean
    protected boolean copyObject(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        final boolean result = this.ezGlide.copy(this.name, target);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Move the object at this key to the target key.
    ///
    /// @param  target  java.lang.String
    /// @return         boolean
    protected boolean moveObject(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        boolean result = this.copyObject(target);

        if (result) {
            result = this.delete();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }
}

