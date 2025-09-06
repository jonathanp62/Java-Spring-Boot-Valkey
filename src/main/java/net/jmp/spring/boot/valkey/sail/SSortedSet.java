package net.jmp.spring.boot.valkey.sail;

/*
 * (#)SSortedSet.java   0.5.0   08/11/2025
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

/// The Sail sorted set class.
///
/// @version    0.5.0
/// @since      0.5.0
public final class SSortedSet extends SObject {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The constructor.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    /// @param  name    java.lang.String
    SSortedSet(final EZGlide ezGlide, final String name) {
        super(ezGlide, name);
    }

    /// Add all the specified members to the sorted set stored at key.
    /// The number of elements added to the sorted set, not including elements already existing for which the
    /// score was updated, is returned.
    ///
    /// @param  values  java.util.Map<java.lang.String, java.lang.Double>
    /// @return         long
    public long addAll(final Map<String, Double> values) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(values));
        }

        final long result = this.ezGlide.zadd(this.name, values);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Add the specified member with the specified score to the sorted set stored at key.
    /// The number of elements added to the sorted set, not including elements already existing for which the
    /// score was updated, is returned.
    ///
    /// @param  value   java.lang.String
    /// @param  score   double
    /// @return         long
    public long add(final String value, final double score) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(value, score));
        }

        final long result = this.ezGlide.zadd(this.name, Collections.singletonMap(value, score));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the number of elements in the sorted set stored at key.
    ///
    /// @return long
    public long size() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final long result = this.ezGlide.zcard(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the score of member in the sorted set stored at key.
    ///
    /// @param  value   java.lang.String
    /// @return         double
    public double score(final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(value));
        }

        final double result = this.ezGlide.zscore(this.name, value);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the rank of member in the sorted set stored at key.
    /// The rank (or index) is 0-based, which means that the member with the lowest score has rank 0.
    ///
    /// @param  value   java.lang.String
    /// @return         long
    public long rank(final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(value));
        }

        final long result = this.ezGlide.zrank(this.name, value);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the rank of member in the reversed order of the sorted set stored at key.
    /// The rank (or index) is 0-based, which means that the member with the highest score has rank 0.
    ///
    /// @param  value   java.lang.String
    /// @return         long
    public long reversedRank(final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(value));
        }

        final long result = this.ezGlide.zrevrank(this.name, value);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the number of elements in the sorted set stored at key with a score between min and max (including min and max).
    ///
    /// @param  min     double
    /// @param  max     double
    /// @return         long
    public long count(final double min, final double max) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(min, max));
        }

        final long result = this.ezGlide.zcount(this.name, min, max);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Copy the sorted set at this key to a new target sorted set.
    ///
    /// @param  target  java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.boot.valkey.sail.SSortedSet>
    @Override
    public Optional<SSortedSet> copy(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        SSortedSet result = null;

        if (super.copyObject(target)) {
            result = new SSortedSet(this.ezGlide, target);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Move the sorted set at this key to a new target sorted set.
    ///
    /// @param  target  java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.boot.valkey.sail.SSortedSet>
    @Override
    public Optional<SSortedSet> move(final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target));
        }

        SSortedSet result = null;

        if (super.moveObject(target)) {
            result = new SSortedSet(this.ezGlide, target);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }
}
