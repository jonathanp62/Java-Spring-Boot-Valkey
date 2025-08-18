package net.jmp.spring.boot.valkey.sail;

/*
 * (#)SList.java    0.5.0   08/11/2025
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
import java.util.Optional;

import net.jmp.spring.boot.valkey.ezglide.EZGlide;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The Sail list class.
///
/// @version    0.5.0
/// @since      0.5.0
public final class SList extends SObject{
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The constructor.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    /// @param  name    java.lang.String
    SList(final EZGlide ezGlide, final String name) {
        super(ezGlide, name);
    }

    /// Add all the specified elements to the tail of list stored at key.
    ///
    /// @param  list    java.util.List<java.lang.String>
    /// @return         boolean
    public boolean addAll(final List<String> list) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final long oldCount = this.ezGlide.llen(this.name);
        final long newCount = this.ezGlide.rpush(this.name, list);
        final boolean result = oldCount != newCount;

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return true;
    }

    /// Return the element at index 'index' in the list stored at key.
    /// The index is zero based.
    ///
    /// @param  index   long
    /// @return         java.util.Optional<java.lang.String>
    public Optional<String> get(final long index) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(index));
        }

        final Optional<String> result = this.ezGlide.lindex(this.name, index);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the number of elements in the list stored at key.
    ///
    /// @return long
    public long size() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final long result = this.ezGlide.llen(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Insert the specified value before the specified pivot value in the list stored
    /// at the key. The new length of the list is returned.
    ///
    /// @param  pivot   java.lang.String
    /// @param  value   java.lang.String
    /// @return         long
    public long insertBefore(final String pivot, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(pivot, value));
        }

        final long result = this.ezGlide.linsert(this.name, EZGlide.ListInsertPosition.BEFORE, pivot, value);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Insert the specified value after the specified pivot value in the list stored
    /// at the key. The new length of the list is returned.
    ///
    /// @param  pivot   java.lang.String
    /// @param  value   java.lang.String
    /// @return         long
    public long insertAfter(final String pivot, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(pivot, value));
        }

        final long result = this.ezGlide.linsert(this.name, EZGlide.ListInsertPosition.AFTER, pivot, value);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the index of the first element of value in the list stored at key.
    ///
    /// @param  value   java.lang.String
    /// @return         long
    public long indexOf(final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(value));
        }

        final long result = this.ezGlide.lpos(this.name, value);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Add the specified value to the head of the list stored at key.
    ///
    /// @param  value   java.lang.String
    public void addFirst(final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(value));
        }

        this.addFirst(List.of(value));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Add all the specified values to the head of the list stored at key.
    ///
    /// @param  values  java.util.List<java.lang.String>
    public void addFirst(final List<String> values) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(values));
        }

        this.ezGlide.lpush(this.name, values);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Add the specified value to the tail of the list stored at key.
    ///
    /// @param  value   java.lang.String
    public void addLast(final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(value));
        }

        this.addLast(List.of(value));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Add all the specified values to the tail of the list stored at key.
    ///
    /// @param  values  java.util.List<java.lang.String>
    public void addLast(final List<String> values) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(values));
        }

        this.ezGlide.rpush(this.name, values);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Copy the list at this key to a new target list.
    ///
    /// @param  target  java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.boot.valkey.sail.SList>
    @Override
    public Optional<SList> copy(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        SList result = null;

        if (super.copyObject(target)) {
            result = new SList(this.ezGlide, target);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Move the list at this key to a new target list.
    ///
    /// @param  target  java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.boot.valkey.sail.SList>
    @Override
    public Optional<SList> move(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        SList result = null;

        if (super.moveObject(target)) {
            result = new SList(this.ezGlide, target);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }
}
