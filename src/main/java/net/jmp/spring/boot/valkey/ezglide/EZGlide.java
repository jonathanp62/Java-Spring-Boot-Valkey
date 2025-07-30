package net.jmp.spring.boot.valkey.ezglide;

/*
 * (#)EZGlide.java  0.4.0   07/22/2025
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

import glide.api.models.GlideString;

import static glide.api.models.GlideString.gs;

import glide.api.models.commands.LInsertOptions;
import glide.api.models.commands.ListDirection;
import glide.api.models.commands.RangeOptions;
import glide.api.models.commands.ScoreFilter;

import java.util.*;

import java.util.concurrent.CompletableFuture;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The EZ Glide class.
///
/// @version    0.4.0
/// @since      0.4.0
public final class EZGlide {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The Glide client.
    private final GlideClient glideClient;

    /// The list insert position.
    public enum ListInsertPosition {
        /// Before the specified element.
        BEFORE,

        /// After the specified element.
        AFTER
    }

    /// The list move direction.
    public enum ListMoveDirection {
        /// Move the list to the right.
        RIGHT,

        /// Move the list to the left.
        LEFT
    }

    /// The score filter for sorted set pop.
    public enum PopScoreFilter {
        /// Return the element with the minimum score.
        MIN,

        /// Return the element with the maximum score.
        MAX
    }

    /// The constructor.
    ///
    /// @param  glideClient  glide.api.GlideClient
    public EZGlide(final GlideClient glideClient) {
        super();

        this.glideClient = glideClient;
    }

    /// Get the client name.
    ///
    /// @return java.lang.String
    public String clientGetName() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<String> future = this.glideClient.clientGetName();
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get the client identifier.
    ///
    /// @return long
    public long clientId() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<Long> future = this.glideClient.clientId();
        final Long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get the client information.
    ///
    /// @return java.lang.String
    public String info() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<String> future = this.glideClient.info();
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Ping.
    /// PONG is returned.
    ///
    /// @return java.lang.String
    public String ping() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<String> future = this.glideClient.ping();
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Ping with a message.
    ///
    /// @param  message java.lang.String
    /// @return         java.lang.String
    public String ping(final String message) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(message));
        }

        final CompletableFuture<GlideString> future = this.glideClient.ping(gs(message));
        final GlideString value = future.join();
        final String result = value.getString();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Echo with a message.
    ///
    /// @param  message java.lang.String
    /// @return         java.lang.String
    public String echo(final String message) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(message));
        }

        final CompletableFuture<GlideString> future = this.glideClient.echo(gs(message));
        final GlideString value = future.join();
        final String result = value.getString();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Flush the database.
    ///
    /// @return java.lang.String
    public String flushall() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<String> future = this.glideClient.flushall();
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get the database size.
    ///
    /// @return long
    public long dbsize() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final CompletableFuture<Long> future = this.glideClient.dbsize();
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Set a key with a value.
    /// OK is returned.
    ///
    /// @param  key     java.lang.String
    /// @param  value   java.lang.String
    /// @return         java.lang.String
    public String set(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final CompletableFuture<String> future = this.glideClient.set(gs(key), gs(value));
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get a value from the specified key.
    ///
    /// @param  key java.lang.String
    /// @return     java.util.Optional<java.lang.String>
    public Optional<String> get(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        String result = null;

        final CompletableFuture<GlideString> future = this.glideClient.get(gs(key));
        final GlideString value = future.join();

        if (value != null) {
            result = value.getString();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Append the specified value to a key
    /// and return the new length of the value.
    ///
    /// @param  key     java.lang.String
    /// @param  value   java.lang.String
    /// @return         long
    public long append(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final CompletableFuture<Long> future = this.glideClient.append(gs(key), gs(value));
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Copy the value in the source key to the target key.
    ///
    /// @param  source  java.lang.String
    /// @param  target  java.lang.String
    /// @return         boolean
    public boolean copy(final String source, final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(source, target));
        }

        final CompletableFuture<Boolean> future = this.glideClient.copy(gs(source), gs(target));
        final boolean result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return true if the key exists and false if not.
    ///
    /// @param  key java.lang.String
    /// @return     boolean
    public boolean exists(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final GlideString[] keys = new GlideString[] { gs(key) };
        final CompletableFuture<Long> future = this.glideClient.exists(keys);
        final long value = future.join();
        final boolean result = value == 1;

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// From the list of specified keys, return the number of keys that exist.
    ///
    /// @param  keys    java.util.List<java.lang.String>
    /// @return         long
    public long exists(final List<String> keys) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(keys));
        }

        long result = 0;

        if (!keys.isEmpty()) {
            final GlideString[] glideStringKeys = new GlideString[keys.size()];

            for (int i = 0; i < keys.size(); i++) {
                glideStringKeys[i] = gs(keys.get(i));
            }

            final CompletableFuture<Long> future = this.glideClient.exists(glideStringKeys);

            result = future.join();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Rename the source key to the target key.
    ///
    /// @param  source  java.lang.String
    /// @param  target  java.lang.String
    /// @return         java.lang.String
    public String rename(final String source, final String target) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(source, target));
        }

        final CompletableFuture<String> future = this.glideClient.rename(gs(source), gs(target));
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get the value from the key then delete the key.
    ///
    /// @param  key java.lang.String
    /// @return     java.lang.String
    public String getdel(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final CompletableFuture<GlideString> future = this.glideClient.getdel(gs(key));
        final GlideString value = future.join();
        final String result = value.getString();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return true if the key was deleted and false if not.
    ///
    /// @param  key java.lang.String
    /// @return     boolean
    public boolean del(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final GlideString[] keys = new GlideString[] { gs(key) };
        final CompletableFuture<Long> future = this.glideClient.del(keys);
        final long value = future.join();
        final boolean result = value == 1;

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Delete the keys specified in the list, returning the number of keys that were deleted.
    ///
    /// @param  keys    java.util.List<java.lang.String>
    /// @return         long
    public long del(final List<String> keys) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(keys));
        }

        long result = 0;

        if (!keys.isEmpty()) {
            final GlideString[] glideStringKeys = new GlideString[keys.size()];

            for (int i = 0; i < keys.size(); i++) {
                glideStringKeys[i] = gs(keys.get(i));
            }

            final CompletableFuture<Long> future = this.glideClient.del(glideStringKeys);

            result = future.join();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Set a hash as the value associated with the key.
    ///
    /// @param  key java.lang.String
    /// @param  map java.util.Map<java.lang.String, java.lang.String>
    /// @return     long
    public long hset(final String key, final Map<String, String> map) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, map));
        }

        long result = 0;

        if (!map.isEmpty()) {
            final Map<GlideString, GlideString> glideStringMap = HashMap.newHashMap(map.size());

            for (final Map.Entry<String, String> entry : map.entrySet()) {
                glideStringMap.put(gs(entry.getKey()), gs(entry.getValue()));
            }

            final CompletableFuture<Long> future = this.glideClient.hset(gs(key), glideStringMap);

            result = future.join();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return a list of keys in the hash.
    ///
    /// @param  key java.lang.String
    /// @return     long
    public List<String> hkeys(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        List<String> result;

        final CompletableFuture<GlideString[]> future = this.glideClient.hkeys(gs(key));
        final GlideString[] keyNames = future.join();

        if (keyNames.length > 0) {
            result = new ArrayList<>(keyNames.length);

            for (final GlideString keyName : keyNames) {
                result.add(keyName.getString());
            }
        } else {
            result = Collections.emptyList();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the number of entries in the hash.
    ///
    /// @param  key java.lang.String
    /// @return     long
    public long hlen(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final CompletableFuture<Long> future = this.glideClient.hlen(gs(key));
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Get the value associated with the entry key in the hash.
    ///
    /// @param  hashKeyName     java.lang.String
    /// @param  entryKeyName    java.lang.String
    /// @return                 java.util.Optional<java.lang.String>
    public Optional<String> hget(final String hashKeyName, final String entryKeyName) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(hashKeyName, entryKeyName));
        }

        String result = null;

        final CompletableFuture<GlideString> future = this.glideClient.hget(gs(hashKeyName), gs(entryKeyName));
        final GlideString value = future.join();

        if (value != null) {
            result = value.getString();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Return true if the entry key exists in the hash.
    ///
    /// @param  hashKeyName     java.lang.String
    /// @param  entryKeyName    java.lang.String
    /// @return                 java.util.Optional<java.lang.String>
    public boolean hexists(final String hashKeyName, final String entryKeyName) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(hashKeyName, entryKeyName));
        }

        final CompletableFuture<Boolean> future = this.glideClient.hexists(gs(hashKeyName), gs(entryKeyName));
        final boolean result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Delete the entry keys from the hash.
    ///
    /// @param  hashKey java.lang.String
    /// @param  keys    java.util.List<java.lang.String>
    /// @return         long
    public long hdel(final String hashKey, final List<String> keys) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(hashKey, keys));
        }

        final GlideString[] glideStringKeys = new GlideString[keys.size()];

        for (int i = 0; i < keys.size(); i++) {
            glideStringKeys[i] = gs(keys.get(i));
        }

        final CompletableFuture<Long> future = this.glideClient.hdel(gs(hashKey), glideStringKeys);
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Delete the entry key from the hash.
    ///
    /// @param  hashKey java.lang.String
    /// @param  key     java.lang.String
    /// @return         long
    public long hdel(final String hashKey, final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(hashKey, key));
        }

        final GlideString[] keys = new GlideString[] { gs(key) };
        final CompletableFuture<Long> future = this.glideClient.hdel(gs(hashKey), keys);
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the entire hash as a map.
    ///
    /// @param  hashKey java.lang.String
    /// @return         java.util.Map<java.lang.String, java.lang.String>
    public Map<String, String> hgetall(final String hashKey) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(hashKey));
        }

        final Map<String, String> result;

        if (this.exists(hashKey)) {
            result = HashMap.newHashMap((int) this.hlen(hashKey));

            final CompletableFuture<Map<GlideString, GlideString>> future = this.glideClient.hgetall(gs(hashKey));
            final Map<GlideString, GlideString> map = future.join();

            for (final Map.Entry<GlideString, GlideString> entry : map.entrySet()) {
                result.put(entry.getKey().getString(), entry.getValue().getString());
            }
        } else {
            result = Collections.emptyMap();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Insert all the specified values at the head of the list stored at key
    /// and return the number of elements added.
    ///
    /// @param  key     java.lang.String
    /// @param  values  java.util.List<java.lang.String>
    /// @return         long
    public long lpush(final String key, final List<String> values) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, values));
        }

        long result = 0;

        if (!values.isEmpty()) {
            final GlideString[] glideStrings = new GlideString[values.size()];

            for (int i = 0; i < values.size(); i++) {
                glideStrings[i] = gs(values.get(i));
            }

            final CompletableFuture<Long> future = this.glideClient.lpush(gs(key), glideStrings);

            result = future.join();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Insert all the specified values at the tail of the list stored at key
    /// and return the number of elements added.
    ///
    /// @param  key     java.lang.String
    /// @param  values  java.util.List<java.lang.String>
    /// @return         long
    public long rpush(final String key, final List<String> values) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, values));
        }

        long result = 0;

        if (!values.isEmpty()) {
            final GlideString[] glideStrings = new GlideString[values.size()];

            for (int i = 0; i < values.size(); i++) {
                glideStrings[i] = gs(values.get(i));
            }

            final CompletableFuture<Long> future = this.glideClient.rpush(gs(key), glideStrings);

            result = future.join();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the element at index 'index' in the list stored at key.
    /// The index is zero based.
    ///
    /// @param  key     java.lang.String
    /// @param  index   long
    /// @return         java.util.Optional<java.lang.String>
    public Optional<String> lindex(final String key, final long index) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, index));
        }

        String result = null;

        final CompletableFuture<GlideString> future = this.glideClient.lindex(gs(key), index);
        final GlideString value = future.join();

        if (value != null) {
            result = value.getString();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Set the element at index 'index' in the list stored at key.
    /// OK is returned. The index is zero based.
    ///
    /// @param  key     java.lang.String
    /// @param  index   long
    /// @param  value   java.lang.String
    /// @return         java.lang.String
    public String lset(final String key, final long index, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, index, value));
        }

        final CompletableFuture<String> future = this.glideClient.lset(gs(key), index, gs(value));
        final String result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the number of elements in the list stored at the key.
    ///
    /// @param  key java.lang.String
    /// @return     long
    public long llen(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final CompletableFuture<Long> future = this.glideClient.llen(gs(key));
        final Long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the index of the first element of the list stored at the key
    /// that is equal to the value. The index is zero based.
    ///
    /// @param  key   java.lang.String
    /// @param  value java.lang.String
    /// @return       long
    public long lpos(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final CompletableFuture<Long> future = this.glideClient.lpos(gs(key), gs(value));
        final Long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Insert the specified value at the specified position in the list stored
    /// at the key. The position argument can be either 'before' or 'after' the
    /// value of pivot. The new length of the list is returned.
    ///
    /// @param  key     java.lang.String
    /// @param  position ListInsertPosition
    /// @param  pivot   java.lang.String
    /// @param  value   java.lang.String
    /// @return         long
    public long linsert(final String key, final ListInsertPosition position, final String pivot, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, position, pivot, value));
        }

        final LInsertOptions.InsertPosition insertPosition = switch (position) {
            case BEFORE -> LInsertOptions.InsertPosition.BEFORE;
            case AFTER -> LInsertOptions.InsertPosition.AFTER;
            default -> throw new IllegalArgumentException("ListInsertPosition is not supported: " + position);
        };

        final CompletableFuture<Long> future = this.glideClient.linsert(
                gs(key),
                insertPosition,
                gs(pivot),
                gs(value)
        );

        final Long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified elements of the list stored at the key.
    /// The indexes are zero based.
    ///
    /// @param  key     java.lang.String
    /// @param  start   long
    /// @param  end     long
    /// @return         java.util.List<java.lang.String>
    public List<String> lrange(final String key, final long start, final long end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, start, end));
        }

        List<String> result = null;

        if (this.llen(key) > 0) {
            final CompletableFuture<GlideString[]> future = this.glideClient.lrange(gs(key), start, end);
            final GlideString[] glideStrings = future.join();

            result = Arrays.stream(glideStrings).map(GlideString::getString).toList();
        } else {
            result = Collections.emptyList();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove and return the element at the head of the list stored at the key.
    /// The value of the element popped from the list is returned.
    ///
    /// @param  key java.lang.String
    /// @return     java.util.Optional<java.lang.String>
    public Optional<String> lpop(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        String result = null;

        final CompletableFuture<GlideString> future = this.glideClient.lpop(gs(key));
        final GlideString value = future.join();

        if (value != null) {
            result = value.getString();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Remove and return the element at the tail of the list stored at the key.
    /// The value of the element popped from the list is returned.
    ///
    /// @param  key java.lang.String
    /// @return     java.util.Optional<java.lang.String>
    public Optional<String> rpop(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        String result = null;

        final CompletableFuture<GlideString> future = this.glideClient.rpop(gs(key));
        final GlideString value = future.join();

        if (value != null) {
            result = value.getString();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Remove the first count occurrences of elements equal to value from the
    /// list stored at key. The count argument affects the number of elements
    /// removed: it returns the number of elements that were removed from the list,
    /// not the original length of the list. If the count argument is positive,
    /// traversal will start from the head of the list. If the count argument is
    /// negative, traversal will start from the tail of the list.
    ///
    /// @param  key   java.lang.String
    /// @param  count long
    /// @param  value java.lang.String
    /// @return       long
    public long lrem(final String key, final long count, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, count, value));
        }

        final CompletableFuture<Long> future = this.glideClient.lrem(gs(key), count, gs(value));
        final Long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Move the element at the head or tail of the source list to the head or tail of the destination list.
    /// The element moved is returned.
    ///
    /// @param  sourceListKey           java.lang.String
    /// @param  destinationListKey      java.lang.String
    /// @param  sourceDirection         net.jmp.spring.boot.valkey.EZGlide.ListMoveDirection
    /// @param  destinationDirection    net.jmp.spring.boot.valkey.EZGlide.ListMoveDirection
    /// @return                         java.util.Optional<java.lang.String>
    public Optional<String> lmove(final String sourceListKey,
                                  final String destinationListKey,
                                  final ListMoveDirection sourceDirection,
                                  final ListMoveDirection destinationDirection) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(sourceListKey, destinationListKey, sourceDirection, destinationDirection));
        }

        String result = null;

        final ListDirection sourceDir = sourceDirection == ListMoveDirection.LEFT ? ListDirection.LEFT : ListDirection.RIGHT;
        final ListDirection destinationDir = destinationDirection == ListMoveDirection.LEFT ? ListDirection.LEFT : ListDirection.RIGHT;

        final CompletableFuture<GlideString> future = this.glideClient.lmove(
                                        gs(sourceListKey),
                                        gs(destinationListKey),
                                        sourceDir,
                                        destinationDir
        );

        final GlideString value = future.join();

        if (value != null) {
            result = value.getString();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Remove and return the element at the tail of the list stored at the source
    /// key and move it to the head of the list stored at the destination key.
    ///
    /// @param  sourceListKey       java.lang.String
    /// @param  destinationListKey  java.lang.String
    /// @return                     java.util.Optional<java.lang.String>
    public Optional<String> rpoplpush(final String sourceListKey, final String destinationListKey) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(sourceListKey, destinationListKey));
        }

        final Optional<String> result = this.lmove(
                sourceListKey,
                destinationListKey,
                ListMoveDirection.RIGHT,
                ListMoveDirection.LEFT
        );

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Add the specified members to the set stored at key.
    /// The number of members added to the set is returned.
    ///
    /// @param  key java.lang.String
    /// @param  set java.util.Set<java.lang.String>
    /// @return     long
    public long sadd(final String key, final Set<String> set) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, set));
        }

        final GlideString[] array = new GlideString[set.size()];
        final Iterator<String> iterator = set.iterator();

        int i = 0;

        while (iterator.hasNext()) {
            array[i++] = gs(iterator.next());
        }

        final CompletableFuture<Long> future = this.glideClient.sadd(gs(key), array);
        final Long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Add the specified member to the set stored at key.
    /// The number of members added to the set is returned.
    ///
    /// @param  key     java.lang.String
    /// @param  value   java.lang.String
    /// @return         long
    public long sadd(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final long result = this.sadd(key, Collections.singleton(value));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Add the specified members to the set stored at key.
    /// The number of members added to the set is returned.
    ///
    /// @param  key     java.lang.String
    /// @param  list    java.util.List<java.lang.String>
    /// @return         long
    public long sadd(final String key, final List<String> list) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, list));
        }

        final long result = this.sadd(key, new HashSet<>(list));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the number of members in the set stored at key.
    ///
    /// @param  key java.lang.String
    /// @return     long
    public long scard(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final CompletableFuture<Long> future = this.glideClient.scard(gs(key));
        final Long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return true if value is a member of the set stored at key.
    ///
    /// @param  key     java.lang.String
    /// @param  value   java.lang.String
    /// @return         boolean
    public boolean sismember(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final CompletableFuture<Boolean> future = this.glideClient.sismember(gs(key), gs(value));
        final boolean result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove the specified member from the set stored at key.
    /// The number of members removed from the set is returned.
    ///
    /// @param  key     java.lang.String
    /// @param  value   java.lang.String
    /// @return         long
    public long srem(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final long result = this.srem(key, Collections.singleton(value));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove the specified members from the set stored at key.
    /// The number of members removed from the set is returned.
    ///
    /// @param  key     java.lang.String
    /// @param  values  java.util.List<java.lang.String>
    /// @return         long
    public long srem(final String key, final List<String> values) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, values));
        }

        final long result = this.srem(key, new HashSet<>(values));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove the specified members from the set stored at key.
    /// The number of members removed from the set is returned.
    ///
    /// @param  key     java.lang.String
    /// @param  values  java.util.Set<java.lang.String>
    /// @return         long
    public long srem(final String key, final Set<String> values) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final GlideString[] array = new GlideString[values.size()];
        final Iterator<String> iterator = values.iterator();

        int i = 0;

        while (iterator.hasNext()) {
            array[i++] = gs(iterator.next());
        }

        final CompletableFuture<Long> future = this.glideClient.srem(gs(key), array);
        final Long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return all members of the set value stored at key.
    ///
    /// @param  key ßjava.lang.String
    /// @return     java.util.Set<java.lang.String>
    public Set<String> smembers(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final CompletableFuture<Set<GlideString>> future = this.glideClient.smembers(gs(key));
        final Set<GlideString> members = future.join();
        final Set<String> result = new HashSet<>();

        for (final GlideString member : members) {
            result.add(member.toString());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Add the specified member with the specified score to the sorted set stored at key.
    /// The number of elements added to the sorted set, not including elements already existing for which the
    /// score was updated, is returned.
    ///
    /// @param  key     java.lang.String
    /// @param  score   double
    /// @param  value   java.lang.String
    /// @return         long
    public long zadd(final String key, final double score, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, score, value));
        }

        final Map<String, Double> map = Collections.singletonMap(value, score);
        final long result = this.zadd(key, map);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Add the specified members with the specified scores to the sorted set stored at key.
    /// The number of elements added to the sorted set, not including elements already existing for which the
    /// score was updated, is returned.
    ///
    /// @param  key     java.lang.String
    /// @param  map     java.util.Map<java.lang.String, java.lang.Double>
    /// @return         long
    public long zadd(final String key, final Map<String, Double> map) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, map));
        }

        final Map<GlideString, Double> glideStringMap = HashMap.newHashMap(map.size());

        for (final Map.Entry<String, Double> entry : map.entrySet()) {
            glideStringMap.put(gs(entry.getKey()), entry.getValue());
        }

        final CompletableFuture<Long> future = this.glideClient.zadd(gs(key), glideStringMap);
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the number of members in the sorted set stored at key.
    ///
    /// @param  key java.lang.String
    /// @return     long
    public long zcard(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final CompletableFuture<Long> future = this.glideClient.zcard(gs(key));
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the score of member in the sorted set stored at key.
    ///
    /// @param  key   java.lang.String
    /// @param  value java.lang.String
    /// @return       double
    public double zscore(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final CompletableFuture<Double> future = this.glideClient.zscore(gs(key), gs(value));
        final double result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the rank of member in the sorted set stored at key.
    /// The rank (or index) is 0-based, which means that the member with the lowest score has rank 0.
    ///
    /// @param  key   java.lang.String
    /// @param  value java.lang.String
    /// @return       long
    public long zrank(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final CompletableFuture<Long> future = this.glideClient.zrank(gs(key), gs(value));
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the rank of member in the sorted set, reversed, stored at key, with the scores ordered from low to high.
    /// The rank (or index) is 0-based, which means that the member with the lowest score has rank 0.
    ///
    /// @param  key   java.lang.String
    /// @param  value java.lang.String
    /// @return       long
    public long zrevrank(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final CompletableFuture<Long> future = this.glideClient.zrevrank(gs(key), gs(value));
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the number of members in the sorted set stored at key with a score between the given values.
    ///
    /// @param  key         java.lang.String
    /// @param  lowerBound  double
    /// @param  upperBound  double
    /// @return             long
    public long zcount(final String key, final double lowerBound, final double upperBound) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, lowerBound, upperBound));
        }

        final RangeOptions.ScoreBoundary lower = new RangeOptions.ScoreBoundary(lowerBound, true);
        final RangeOptions.ScoreBoundary upper = new RangeOptions.ScoreBoundary(upperBound, true);

        final CompletableFuture<Long> future = this.glideClient.zcount(gs(key), lower, upper);
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Increment the score of member in the sorted set stored at key by increment.
    ///
    /// @param  key         java.lang.String
    /// @param  increment   double
    /// @param  value       java.lang.String
    /// @return             double
    public double zincby(final String key, final double increment, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, increment, value));
        }

        final CompletableFuture<Double> future = this.glideClient.zincrby(gs(key), increment, gs(value));
        final double result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements by index in a sorted set stored at key.
    /// The list is reversed when the reverse option is true.
    ///
    /// @param  key     java.lang.String
    /// @param  start   long
    /// @param  end     long
    /// @param  reverse boolean
    /// @return         java.util.List
    public List<String> zrange(final String key, final long start, final long end, final boolean reverse) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, start, end, reverse));
        }

        final RangeOptions.RangeByIndex options = new RangeOptions.RangeByIndex(start, end);
        final CompletableFuture<GlideString[]> future = this.glideClient.zrange(gs(key), options, reverse);
        final GlideString[] array = future.join();
        final List<String> result = new ArrayList<>(array.length);

        for (final GlideString element : array) {
            result.add(element.getString());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements by score in a sorted set stored at key.
    /// The list is reversed when the reverse option is true.
    ///
    /// @param  key         java.lang.String
    /// @param  lowerScore  double
    /// @param  upperScore  double
    /// @param  reverse     boolean
    /// @return             java.util.List
    public List<String> zrange(final String key, final double lowerScore, final double upperScore, final boolean reverse) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, lowerScore, upperScore, reverse));
        }

        final RangeOptions.RangeByScore options = new RangeOptions.RangeByScore(
                new RangeOptions.ScoreBoundary(lowerScore, true),
                new RangeOptions.ScoreBoundary(upperScore, true)
        );

        final CompletableFuture<GlideString[]> future = this.glideClient.zrange(gs(key), options, reverse);
        final GlideString[] array = future.join();
        final List<String> result = new ArrayList<>(array.length);

        for (final GlideString element : array) {
            result.add(element.getString());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements by index in a sorted set stored at key.
    ///
    /// @param  key     java.lang.String
    /// @param  start   long
    /// @param  end     long
    /// @return         java.util.List
    public List<String> zrange(final String key, final long start, final long end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, start, end));
        }

        final List<String> result = this.zrange(key, start, end, false);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements by score in a sorted set stored at key.
    ///
    /// @param  key         java.lang.String
    /// @param  lowerScore  double
    /// @param  upperScore  double
    /// @return             java.util.List
    public List<String> zrange(final String key, final double lowerScore, final double upperScore) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, lowerScore, upperScore));
        }

        final List<String> result = this.zrange(key, lowerScore, upperScore, false);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements by index, reversed, in a sorted set stored at key.
    ///
    /// @param  key     java.lang.String
    /// @param  start   long
    /// @param  end     long
    /// @return         java.util.List
    public List<String> zrevrange(final String key, final long start, final long end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, start, end));
        }

        final List<String> result = this.zrange(key, start, end, true);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return the specified range of elements, reversed, by score in a sorted set stored at key.
    ///
    /// @param  key         java.lang.String
    /// @param  lowerScore  double
    /// @param  upperScore  double
    /// @return             java.util.List
    public List<String> zrevrange(final String key, final double lowerScore, final double upperScore) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, lowerScore, upperScore));
        }

        final List<String> result = this.zrange(key, upperScore, lowerScore, true);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Pop the minimum or maximum element from the sorted set stored at key.
    ///
    /// @param  key     java.lang.String
    /// @param  filter  net.jmp.spring.boot.valkey.ezglide.EZGlide.PopScoreFilter
    /// @return         java.util.Map<java.lang.String, java.lang.Object>
    public Map<String, Object> zmpop(final String key, final PopScoreFilter filter) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, filter));
        }

        final ScoreFilter scoreFilter = switch (filter) {
            case MIN -> ScoreFilter.MIN;
            case MAX -> ScoreFilter.MAX;
        };

        final CompletableFuture<Map<GlideString, Object>> future = this.glideClient.zmpop(
                new GlideString[] { gs(key) },
                scoreFilter
        );

        final Map<GlideString, Object> map = future.join();
        final Map<String, Object> result = HashMap.newHashMap(map.size());

        for (final Map.Entry<GlideString, Object> entry : map.entrySet()) {
            result.put(entry.getKey().getString(), entry.getValue());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Pop the maximum element from the sorted set stored at key.
    ///
    /// @param  key java.lang.String
    /// @return     java.util.Map<java.lang.String, java.lang.Object>
    public Map<String, Object> zmpopmax(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final Map<String, Object> result = this.zmpop(key, PopScoreFilter.MAX);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Pop the minimum element from the sorted set stored at key.
    ///
    /// @param  key java.lang.String
    /// @return     java.util.Map<java.lang.String, java.lang.Object>
    public Map<String, Object> zmpopmin(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        final Map<String, Object> result = this.zmpop(key, PopScoreFilter.MIN);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Return a random element from the sorted set stored at key.
    ///
    /// @param  key java.lang.String
    /// @return     java.util.Optional<java.lang.String>
    public Optional<String> zrandmember(final String key) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key));
        }

        String result = null;

        final CompletableFuture<GlideString> future = this.glideClient.zrandmember(gs(key));
        final GlideString value = future.join();

        if (value != null) {
            result = value.getString();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return Optional.ofNullable(result);
    }

    /// Remove the specified member from the sorted set stored at key.
    ///
    /// @param  key     java.lang.String
    /// @param  value   java.lang.String
    /// @return         long
    public long zrem(final String key, final String value) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, value));
        }

        final GlideString[] values = { gs(value) };
        final CompletableFuture<Long> future = this.glideClient.zrem(gs(key), values);
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove the specified members from the sorted set stored at key.
    ///
    /// @param  key     java.lang.String
    /// @param  values  java.util.List<java.lang.String>
    /// @return         long
    public long zrem(final String key, final List<String> values) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, values));
        }

        final GlideString[] array = new GlideString[values.size()];

        for (int i = 0; i < values.size(); i++) {
            array[i] = gs(values.get(i));
        }

        final CompletableFuture<Long> future = this.glideClient.zrem(gs(key), array);
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove all elements in the sorted set stored at key with rank between start and end.
    ///
    /// @param  key   java.lang.String
    /// @param  start long
    /// @param  end   long
    /// @return       long
    public long zremrangebyrank(final String key, final long start, final long end) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, start, end));
        }

        final CompletableFuture<Long> future = this.glideClient.zremrangebyrank(key, start, end);
        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /// Remove all elements in the sorted set stored at key with a score between min and max.
    ///
    /// @param  key         java.lang.String
    /// @param  lowerScore  double
    /// @param  upperScore  double
    /// @return             long
    public long zremrangebyscore(final String key, final double lowerScore, final double upperScore) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(key, lowerScore, upperScore));
        }

        final CompletableFuture<Long> future = this.glideClient.zremrangebyscore(
                key,
                new RangeOptions.ScoreBoundary(lowerScore, true),
                new RangeOptions.ScoreBoundary(upperScore, true)
        );

        final long result = future.join();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }
}
