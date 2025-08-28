package net.jmp.spring.boot.valkey.sail;

/*
 * (#)SSet.java 0.5.0   08/11/2025
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

import java.util.*;

import net.jmp.spring.boot.valkey.ezglide.EZGlide;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The Sail set class.
///
/// @version    0.5.0
/// @since      0.5.0
public final class SSet extends SObject{
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The constructor.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    /// @param  name    java.lang.String
    SSet(final EZGlide ezGlide, final String name) {
        super(ezGlide, name);
    }

    /// Add the specified member to the set stored at key.
    ///
    /// @param  value   java.lang.String
    /// @return         boolean
    public boolean add(final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(value));
        }

        final long count = this.ezGlide.sadd(this.name, value);
        final boolean result = count > 0;

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Add all the specified members to the set stored at key.
    ///
    /// @param  values  java.util.Collection<java.lang.String>
    /// @return         boolean
    public boolean addAll(final Collection<String> values) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(values));
        }

        final long size = this.ezGlide.scard(this.name);
        final List<String> list = new ArrayList<>(values);
        final long count = this.ezGlide.sadd(this.name, list);
        final boolean result = count != size;

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get the number of members in the set stored at key.
    ///
    /// @return int
    public int size() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final long result = this.ezGlide.scard(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return (int) result;
    }

    /// Check if the set stored at key is empty.
    ///
    /// @return boolean
    public boolean isEmpty() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final boolean result = this.ezGlide.scard(this.name) == 0;

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove all elements from the set stored at key.
    public void clear() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        this.ezGlide.del(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Check if the set stored at key contains the specified element.
    ///
    /// @param  element  java.lang.String
    /// @return          boolean
    public boolean contains(final String element) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(element));
        }

        final boolean result = this.ezGlide.sismember(this.name, element);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Check if the set stored at key contains all the specified elements.
    ///
    /// @param  elements  java.util.Collection<java.lang.String>
    /// @return           boolean
    public boolean containsAll(final Collection<String> elements) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(elements));
        }

        boolean result = true;

        for (final String element : elements) {
            if (!this.contains(element)) {
                result = false;
                break;
            }
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get all the members in the set stored at key.
    ///
    /// @return java.util.Set<java.lang.String>
    public Set<String> members() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Set<String> result = this.ezGlide.smembers(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove the specified element from the set stored at key.
    ///
    /// @param  element  java.lang.String
    /// @return          boolean
    public boolean remove(final String element) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(element));
        }

        final long count = this.ezGlide.srem(this.name, element);
        final boolean result = count > 0;

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get and remove a random member from the set stored at key.
    ///
    /// @return java.util.Optional<java.lang.String>
    public Optional<String> getAndRemoveRandomMember() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Set<String> set = this.getAndRemoveRandomMembers(1);
        final Optional<String> result = set.isEmpty() ? Optional.empty() : Optional.of(set.iterator().next());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get and remove the specified number of random members from the set stored at key.
    ///
    /// @param  count  int
    /// @return        java.util.Set<java.lang.String>
    public Set<String> getAndRemoveRandomMembers(final int count) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(count));
        }

        final Set<String> result = this.ezGlide.spop(this.name, count);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Move the specified element from this set to the target set.
    ///
    /// @param  element  java.lang.String
    /// @param  target   net.jmp.spring.boot.valkey.sail.SSet
    /// @return          boolean
    public boolean moveToSet(final String element, final SSet target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(element, target));
        }

        final boolean result = this.ezGlide.smove(this.name, target.name, element);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Copy the set at this key to a new target set.
    ///
    /// @param  target  java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.boot.valkey.sail.SSet>
    @Override
    public Optional<SSet> copy(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        SSet result = null;

        if (super.copyObject(target)) {
            result = new SSet(this.ezGlide, target);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Move the set at this key to a new target set.
    ///
    /// @param  target  java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.boot.valkey.sail.SSet>
    @Override
    public Optional<SSet> move(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        SSet result = null;

        if (super.moveObject(target)) {
            result = new SSet(this.ezGlide, target);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }
}
