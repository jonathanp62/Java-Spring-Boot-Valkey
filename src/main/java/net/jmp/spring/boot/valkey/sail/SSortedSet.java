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

    /// Increment the score of member in the sorted set stored at key by increment.
    /// The new score of member is returned.
    ///
    /// @param  member       java.lang.String
    /// @param  increment   double
    /// @return             double
    public double incrementScoreBy(final String member, final double increment) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(member, increment));
        }

        final double result = this.ezGlide.zincrby(this.name, increment, member);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements by index in a sorted set stored at key.
    ///
    /// @param  start   long
    /// @param  end     long
    /// @return         java.util.List
    public List<String> rangeByIndices(final long start, final long end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(start, end));
        }

        final List<String> result = this.ezGlide.zrange(this.name, start, end);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Store the specified range of elements by index in a sorted set stored at key into a new sorted set stored at target.
    ///
    /// @param  target   net.jmp.spring.boot.valkey.sail.SSortedSet
    /// @param  start    long
    /// @param  end      long
    /// @return          long
    public long rangeByIndicesAndStore(final SSortedSet target, final long start, final long end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target, start, end));
        }

        final long result = this.ezGlide.zrangestore(this.name, target.name, start, end, false);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements by score in a sorted set stored at key.
    ///
    /// @param  start   double
    /// @param  end     double
    /// @return         java.util.List
    public List<String> rangeByScores(final double start, final double end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(start, end));
        }

        final List<String> result = this.ezGlide.zrange(this.name, start, end);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Store the specified range of elements by score in a sorted set stored at key into a new sorted set stored at target.
    ///
    /// @param  target   net.jmp.spring.boot.valkey.sail.SSortedSet
    /// @param  start    double
    /// @param  end      double
    /// @return          long
    public long rangeByScoresAndStore(final SSortedSet target, final double start, final double end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target, start, end));
        }

        final long result = this.ezGlide.zrangestore(this.name, target.name, start, end, false);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements by lexicographical order in a sorted set stored at key.
    ///
    /// @param  min     java.lang.String
    /// @param  max     java.lang.String
    /// @return         java.util.List
    public List<String> rangeByLex(final String min, final String max) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(min, max));
        }

        final List<String> result = this.ezGlide.zrange(this.name, min, max);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Store the specified range of elements by lexicographical order in a sorted set stored at key into a new sorted set stored at target.
    ///
    /// @param  target   net.jmp.spring.boot.valkey.sail.SSortedSet
    /// @param  min      java.lang.String
    /// @param  max      java.lang.String
    /// @return          long
    public long rangeByLexAndStore(final SSortedSet target, final String min, final String max) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target, min, max));
        }

        final long result = this.ezGlide.zrangestore(this.name, target.name, min, max);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements by index in a sorted set stored at key in reverse order.
    ///
    /// @param  start   long
    /// @param  end     long
    /// @return         java.util.List
    public List<String> reversedRangeByIndices(final long start, final long end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(start, end));
        }

        final List<String> result = this.ezGlide.zrevrange(this.name, start, end);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements by score in a sorted set stored at key in reverse order.
    ///
    /// @param  start   double
    /// @param  end     double
    /// @return         java.util.List
    public List<String> reversedRangeByScores(final double start, final double end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(start, end));
        }

        final List<String> result = this.ezGlide.zrevrange(this.name, start, end);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements by lexicographical order in a sorted set stored at key in reverse order.
    ///
    /// @param  min     java.lang.String
    /// @param  max     java.lang.String
    /// @return         java.util.List
    public List<String> reversedRangeByLex(final String min, final String max) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(min, max));
        }

        final List<String> result = this.ezGlide.zrevrange(this.name, min, max);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove and return the minimum or maximum element by score from a sorted set stored at key.
    ///
    /// @param  filter  net.jmp.spring.boot.valkey.ezglide.EZGlide.PopScoreFilter
    /// @return         java.util.Map<java.lang.String, java.lang.Object>
    public Map<String, Object> pop(final EZGlide.PopScoreFilter filter) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(filter));
        }

        final Map<String, Object> result = this.ezGlide.zmpop(this.name, filter);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove and return the minimum or maximum elements by score from a sorted set stored at key.
    ///
    /// @param  filter  net.jmp.spring.boot.valkey.ezglide.EZGlide.PopScoreFilter
    /// @param  count   long
    /// @return         java.util.Map<java.lang.String, java.lang.Object>
    public Map<String, Object> pop(final EZGlide.PopScoreFilter filter, final long count) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(filter, count));
        }

        final Map<String, Object> result = this.ezGlide.zmpop(this.name, filter, count);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove and return the maximum element from a sorted set stored at key.
    ///
    /// @return java.util.Map<java.lang.String, java.lang.Object>
    public Map<String, Object> popMax() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Map<String, Object> result = this.ezGlide.zpopmax(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove and return the maximum elements from a sorted set stored at key.
    ///
    /// @param  count   long
    /// @return         java.util.Map<java.lang.String, java.lang.Object>
    public Map<String, Object> popMax(final long count) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(count));
        }

        final Map<String, Object> result = this.ezGlide.zpopmax(this.name, count);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove and return the minimum element from a sorted set stored at key.
    ///
    /// @return java.util.Map<java.lang.String, java.lang.Object>
    public Map<String, Object> popMin() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Map<String, Object> result = this.ezGlide.zpopmin(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove and return the minimum elements from a sorted set stored at key.
    ///
    /// @param  count   long
    /// @return         java.util.Map<java.lang.String, java.lang.Object>
    public Map<String, Object> popMin(final long count) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(count));
        }

        final Map<String, Object> result = this.ezGlide.zpopmin(this.name, count);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return a random element from a sorted set stored at key.
    ///
    /// @return java.util.Optional<java.lang.String>
    public Optional<String> randomMember() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Optional<String> result = this.ezGlide.zrandmember(this.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return random elements from a sorted set stored at key.
    ///
    /// @param  count   long
    /// @return         java.util.List<java.lang.String>
    public List<String> randomMembers(final long count) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(count));
        }

        final List<String> result = this.ezGlide.zrandmember(this.name, count);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return random elements from the sorted set stored at key.
    ///
    /// @param  count   long
    /// @return         java.util.Map<java.lang.String, java.lang.Double>
    public Map<String, Double> randomMembersWithScores(final long count) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(count));
        }

        final Map<String, Double> result = this.ezGlide.zrandmemberwithscores(this.name, count);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Count the number of members in a sorted set lexically within the specified range.
    ///
    /// @param  min     java.lang.String
    /// @param  max     java.lang.String
    /// @return         long
    public long countLexically(final String min, final String max) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(min, max));
        }

        final long result = this.ezGlide.zlexcount(this.name, min, max);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove the specified member from the sorted set stored at key.
    ///
    /// @param  member  java.lang.String
    /// @return         long
    public long remove(final String member) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(member));
        }

        final long result = this.ezGlide.zrem(this.name, member);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove the specified members from the sorted set stored at key.
    ///
    /// @param  members  java.util.List<java.lang.String>
    /// @return          long
    public long remove(final List<String> members) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(members));
        }

        final long result = this.ezGlide.zrem(this.name, members);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove the specified range of elements by index in a sorted set stored at key.
    ///
    /// @param  start   long
    /// @param  end     long
    /// @return         long
    public long removeRangeByRank(final long start, final long end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(start, end));
        }

        final long result = this.ezGlide.zremrangebyrank(this.name, start, end);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove the specified range of elements by score in a sorted set stored at key.
    ///
    /// @param  lower   double
    /// @param  upper   double
    /// @return         long
    public long removeRangeByScore(final double lower, final double upper) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(lower, upper));
        }

        final long result = this.ezGlide.zremrangebyscore(this.name, lower, upper);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove the specified range of elements by lexicographical order in a sorted set stored at key.
    ///
    /// @param  min     java.lang.String
    /// @param  max     java.lang.String
    /// @return         long
    public long removeRangeByLex(final String min, final String max) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(min, max));
        }

        final long result = this.ezGlide.zremrangebylex(this.name, min, max);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the difference between the sorted set at this key and the sorted sets at otherKeys.
    ///
    /// @param  otherSSortedSets  java.util.List<net.jmp.spring.boot.valkey.sail.SSortedSet>
    /// @return                   java.util.Set<java.lang.String>
    public Set<String> diff(final List<SSortedSet> otherSSortedSets) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(otherSSortedSets));
        }

        final List<String> otherKeys = otherSSortedSets.stream().map(SSortedSet::getName).toList();
        final Set<String> result = this.ezGlide.zdiff(this.name, otherKeys);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the difference between the sorted set at this key and the sorted set at otherKey.
    ///
    /// @param  otherSSortedSet  net.jmp.spring.boot.valkey.sail.SSortedSet
    /// @return                  java.util.Set<java.lang.String>
    public Set<String> diff(final SSortedSet otherSSortedSet) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(otherSSortedSet));
        }

        final Set<String> result = this.ezGlide.zdiff(this.name, otherSSortedSet.name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Store the difference between the sorted set at this key and the sorted sets at otherKeys in the sorted set at target.
    ///
    /// @param  target          net.jmp.spring.boot.valkey.sail.SSortedSet
    /// @param  otherSSortedSets java.util.List<net.jmp.spring.boot.valkey.sail.SSortedSet>
    /// @return                 long
    public long diffAndStore(final SSortedSet target, final List<SSortedSet> otherSSortedSets) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target, otherSSortedSets));
        }

        final List<String> otherKeys = otherSSortedSets.stream().map(SSortedSet::getName).toList();
        final long result = this.ezGlide.zdiffstore(this.name, target.name, otherKeys);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Store the difference between the sorted set at this key and the sorted set at otherKey in the sorted set at target.
    ///
    /// @param  target          net.jmp.spring.boot.valkey.sail.SSortedSet
    /// @param  otherSSortedSet net.jmp.spring.boot.valkey.sail.SSortedSet
    /// @return                 long
    public long diffAndStore(final SSortedSet target, final SSortedSet otherSSortedSet) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(target, otherSSortedSet));
        }

        final long result = this.ezGlide.zdiffstore(this.name, target.name, otherSSortedSet.name);

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
