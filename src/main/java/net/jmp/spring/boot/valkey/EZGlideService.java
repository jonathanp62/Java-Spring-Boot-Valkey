package net.jmp.spring.boot.valkey;

/*
 * (#)EZGlideService.java   0.4.0   07/22/2025
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

import glide.api.models.configuration.GlideClientConfiguration;
import glide.api.models.configuration.NodeAddress;

import java.util.List;
import java.util.Map;

import java.util.concurrent.ExecutionException;

import net.jmp.spring.boot.valkey.ezglide.EZGlide;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

/// The EZGlide service class.
///
/// @version    0.4.0
/// @since      0.4.0
@Service
public class EZGlideService {
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
    @Value("${glide.flushDbOnEZGlideServiceStart}")
    private boolean flushDbOnServiceStart;

    /// Flush the database at the end when true.
    @Value("${glide.flushDbOnEZGlideServiceStop}")
    private boolean flushDbOnServiceStop;

    /// True when the JSON data type is supported.
    @Value("${valkey.json.supported}")
    private boolean valkeyJsonSupported;

    /// The default constructor.
    public EZGlideService() {
        super();
    }

    /// The demo method.
    public void demo() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        try (final GlideClient glideClient = this.connect()) {
            final EZGlide ezGlide = new EZGlide(glideClient);

            if (this.flushDbOnServiceStart) {
                this.cleanup(ezGlide);
            }

            this.miscellaneous(ezGlide);
            this.getAndSet(ezGlide);
            this.getAndDelete(ezGlide);
            this.hash(ezGlide);
            this.list(ezGlide);
            this.set(ezGlide);
            this.sortedSet(ezGlide);

            if (this.flushDbOnServiceStop) {
                this.cleanup(ezGlide);
            }
        } catch (final ExecutionException e) {
            this.logger.error("Glide execution execution: {}", e.getMessage(), e);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Miscellaneous commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void miscellaneous(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Client name: {}", ezGlide.clientGetName());
            this.logger.info("Client ID: {}", ezGlide.clientId());
            this.logger.info("Client info: {}", ezGlide.info());
            this.logger.info("Ping: {}", ezGlide.ping());
            this.logger.info("Ping: {}", ezGlide.ping("Pinging..."));
            this.logger.info("Echo: {}", ezGlide.ping("Message that is echoed"));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Get and set commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void getAndSet(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Set: {}", ezGlide.set("Apples", "Oranges"));
            this.logger.info("Get: {}", ezGlide.get("Apples").orElse("Apples not found"));
            this.logger.info("Get: {}", ezGlide.get("Oranges").orElse("Oranges not found"));
            this.logger.info("Append: {}", ezGlide.append("Apples", " and Raisins"));
            this.logger.info("Get: {}", ezGlide.get("Apples").orElse("Apples not found"));
            this.logger.info("Copy: {}", ezGlide.copy("Apples", "Oranges"));
            this.logger.info("Get: {}", ezGlide.get("Oranges").orElse("Oranges not found"));
            this.logger.info("Exists: {}", ezGlide.exists("Oranges"));
            this.logger.info("Exists: {}", ezGlide.exists("Lemons"));
            this.logger.info("Exists: {}", ezGlide.exists(List.of("Oranges", "Lemons", "Apples")));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Get and delete commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void getAndDelete(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Set: {}", ezGlide.set("Name", "Jonathan"));
            this.logger.info("Get: {}", ezGlide.get("Name").orElse("Name not found"));
            this.logger.info("Rename: {}", ezGlide.rename("Name", "My-Name"));
            this.logger.info("Exists: {}", ezGlide.exists("Name"));
            this.logger.info("GetDel: {}", ezGlide.getdel("My-Name"));
            this.logger.info("Exists: {}", ezGlide.exists("My-Name"));
            this.logger.info("Set: {}", ezGlide.set("Middle-Name", "Martin"));
            this.logger.info("Del: {}", ezGlide.del("Middle-Name"));
            this.logger.info("Set: {}", ezGlide.set("Last-Name", "Parker"));
            this.logger.info("Set: {}", ezGlide.set("Middle-Name", "Martin"));
            this.logger.info("Del: {}", ezGlide.del(List.of("Last-Name", "Middle-Name")));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Hash commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void hash(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        final Map<String, String> map = Map.of(
                "firstName", "Wendy",
                "middleName", "Carol",
                "lastName", "Burkins"
        );

        if (this.logger.isInfoEnabled()) {
            this.logger.info("HSet: {}", ezGlide.hset("Names", map));
            this.logger.info("HKeys: {}", ezGlide.hkeys("Names"));
            this.logger.info("HVals: {}", ezGlide.hvals("Names"));
            this.logger.info("HLen: {}", ezGlide.hlen("Names"));
            this.logger.info("HStrLen: {}", ezGlide.hstrlen("Names", "firstName"));
            this.logger.info("HGet: {}", ezGlide.hget("Names", "middleName").orElse("middleName not found in Names"));
            this.logger.info("HGet: {}", ezGlide.hget("Names", "nickName").orElse("nickName not found in Names"));
            this.logger.info("HExists: {}", ezGlide.hexists("Names", "middleName"));
            this.logger.info("HExists: {}", ezGlide.hexists("Names", "nickName"));
            this.logger.info("HDel: {}", ezGlide.hdel("Names", "middleName"));
            this.logger.info("HDel: {}", ezGlide.hdel("Names", List.of("firstName", "lastName")));
            this.logger.info("HSet: {}", ezGlide.hset("Names", map));
            this.logger.info("HGetAll: {}", ezGlide.hgetall("Names"));
            this.logger.info("HGetAll: {}", ezGlide.hgetall("Undefined"));
            this.logger.info("HSetNX: {}", ezGlide.hsetnx("Names", "firstName", "Bitch"));
            this.logger.info("HSetNX: {}", ezGlide.hsetnx("Names", "nickName", "Bitch"));
        }

        final Map<String, String> longMap = Map.of(
                "one", "1",
                "two", "2",
                "three", "3"
        );

        final Map<String, String> doubleMap = Map.of(
                "one", "1",
                "two", "2",
                "three", "3"
        );

        if (this.logger.isInfoEnabled()) {
            this.logger.info("HSet: {}", ezGlide.hset("Longs", longMap));
            this.logger.info("HIncrBy: {}", ezGlide.hincrBy("Longs", "two", 2));
            this.logger.info("HSet: {}", ezGlide.hset("Doubles", doubleMap));
            this.logger.info("HIncrBy: {}", ezGlide.hincrByFloat("Doubles", "three", 1.5));
            this.logger.info("HIncrBy: {}", ezGlide.hincrByFloat("Doubles", "five", 5.0));
            this.logger.info("HRandField: {}", ezGlide.hrandfield("Doubles").orElse("Doubles not found or is empty"));
            this.logger.info("HMGet: {}", ezGlide.hmget("Names", List.of("firstName", "nickName")));
            this.logger.info("HMGet: {}", ezGlide.hmget("Names", "middleName").orElse("middleName not found in Names"));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// List commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void list(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        /*
         * Left pushing to the list means that the element is added to the head of the list.
         * So the list will look like this:
         *   Grapes
         *   Strawberries
         *   Blueberries
         *   Cherries
         */

        final List<String> fruits = List.of("Cherries", "Blueberries", "Strawberries", "Grapes");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("LPush: {}", ezGlide.lpush("Fruits", fruits));
            this.logger.info("LIndex: {}", ezGlide.lindex("Fruits", 0).orElse("Fruits[0] not found"));
            this.logger.info("LIndex: {}", ezGlide.lindex("Fruits", 1).orElse("Fruits[1] not found"));
            this.logger.info("LSet: {}", ezGlide.lset("Fruits", 0, "Green Grapes"));
            this.logger.info("LLen: {}", ezGlide.llen("Fruits"));
            this.logger.info("LPos: {}", ezGlide.lpos("Fruits", "Blueberries"));
            this.logger.info("LInsert: {}", ezGlide.linsert("Fruits", EZGlide.ListInsertPosition.BEFORE, "Cherries", "Raspberries"));
            this.logger.info("LRange: {}", ezGlide.lrange("Fruits", 1, 3));
            this.logger.info("LPop: {}", ezGlide.lpop("Fruits").orElse("Fruits not found or is empty"));
        }

        /*
         * Right pushing to the list means that the element is added to the head of the list.
         * So the list will look like this:
         *   Broccoli
         *   Cauliflower
         *   Green Beans
         *   Zucchini
         */

        final List<String> veggies = List.of("Broccoli", "Cauliflower", "Green Beans", "Zucchini");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("RPush: {}", ezGlide.rpush("Veggies", veggies));
            this.logger.info("RPop: {}", ezGlide.rpop("Veggies").orElse("Veggies not found or is empty"));
            this.logger.info("LRem: {}", ezGlide.lrem("Veggies", -1, "Cauliflower"));
        }

        final List<String> one = List.of("a", "b", "c");
        final List<String> two = List.of("x", "y", "z");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("RPush: {}", ezGlide.rpush("One", one));
            this.logger.info("RPush: {}", ezGlide.rpush("Two", two));
            this.logger.info("LMove: {}", ezGlide.lmove("One", "Two", EZGlide.ListMoveDirection.RIGHT, EZGlide.ListMoveDirection.LEFT).orElse("One not found or is empty"));
            this.logger.info("LMove: {}", ezGlide.lmove("Two", "One", EZGlide.ListMoveDirection.LEFT, EZGlide.ListMoveDirection.RIGHT).orElse("Two not found or is empty"));
            this.logger.info("RPopLPush: {}", ezGlide.rpoplpush("One", "Two").orElse("One not found or is empty"));
            this.logger.info("LMPop: {}", ezGlide.lmpop(List.of("Fruits"), EZGlide.ListPopDirection.LEFT, 2));
            this.logger.info("LMPop: {}", ezGlide.lmpop(List.of("Fruits"), EZGlide.ListPopDirection.RIGHT));
            this.logger.info("LPushNX: {}", ezGlide.lpushnx("Fruits", "Blueberries"));
            this.logger.info("RPushNX: {}", ezGlide.rpushnx("Fruits", "Cherries"));
            this.logger.info("LTrim: {}", ezGlide.ltrim("Fruits", 0, 1));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Set commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void set(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        final List<String> girls = List.of("Heather", "Jane", "Jill", "Amy", "Jill", "Suzy", "Wendy", "Laura");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("SAdd: {}", ezGlide.sadd("Girls", "Brooke"));
            this.logger.info("SAdd: {}", ezGlide.sadd("Girls", girls));
            this.logger.info("SCard: {}", ezGlide.scard("Girls"));
            this.logger.info("SIsMember: {}", ezGlide.sismember("Girls", "Heather"));
            this.logger.info("SIsMember: {}", ezGlide.sismember("Girls", "Robin"));
            this.logger.info("SRem: {}", ezGlide.srem("Girls", "Jane"));
            this.logger.info("SRem: {}", ezGlide.srem("Girls", List.of("Heather", "Jill", "Amy")));
            this.logger.info("SMembers: {}", ezGlide.smembers("Girls"));
            this.logger.info("SMove: {}", ezGlide.smove("Girls", "Bitches", "Brooke"));
            this.logger.info("Exists: {}", ezGlide.exists("Bitches"));
            this.logger.info("Del: {}", ezGlide.del("Bitches"));
            this.logger.info("SPop: {}", ezGlide.spop("Girls").orElse("Girls not found or is empty"));
            this.logger.info("SPop: {}", ezGlide.spop("Girls", 2));
            this.logger.info("Exists: {}", ezGlide.exists("Girls"));
            this.logger.info("SAdd: {}", ezGlide.sadd("Girls", girls));
            this.logger.info("SRandMember: {}", ezGlide.srandmember("Girls").orElse("Girls not found or is empty"));
            this.logger.info("SRandMember: {}", ezGlide.srandmember("Girls", 2));
            this.logger.info("Rename: {}", ezGlide.rename("Girls", "Lovers"));
            this.logger.info("Copy: {}", ezGlide.copy("Lovers", "Girls"));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Sorted set commands.
    ///
    /// @param  ezGlide net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void sortedSet(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        /* A sorted set is a collection of unique strings that maintain order by each string's associated score */

        final Map<String, Double> map = Map.of(
                "ZZZ", 1.0,
                "YYY", 2.0,
                "XXX", 3.0,
                "CCC", 24.0,
                "BBB", 25.0,
                "AAA", 26.0
        );

        if (this.logger.isInfoEnabled()) {
            this.logger.info("ZAdd: {}", ezGlide.zadd("My-Sorted-Set", map));
            this.logger.info("ZAdd: {}", ezGlide.zadd("My-Sorted-Set", 4.0, "WWW"));
            this.logger.info("ZCard: {}", ezGlide.zcard("My-Sorted-Set"));
            this.logger.info("ZScore: {}", ezGlide.zscore("My-Sorted-Set", "CCC"));
            this.logger.info("ZRank: {}", ezGlide.zrank("My-Sorted-Set", "CCC"));
            this.logger.info("ZRevRank: {}", ezGlide.zrevrank("My-Sorted-Set", "CCC"));
            this.logger.info("ZCount: {}", ezGlide.zcount("My-Sorted-Set", 1.0, 3.0));
            this.logger.info("ZIncBy: {}", ezGlide.zincby("My-Sorted-Set", 0.1, "CCC"));
            this.logger.info("ZRange: {}", ezGlide.zrange("My-Sorted-Set", 0, 26));
            this.logger.info("ZRevRange: {}", ezGlide.zrevrange("My-Sorted-Set", 0, 26));
            this.logger.info("ZRange: {}", ezGlide.zrange("My-Sorted-Set", 2.0, 25.0));
            this.logger.info("ZRevRange: {}", ezGlide.zrevrange("My-Sorted-Set", 2.0, 25.0));
            this.logger.info("ZMPop: {}", ezGlide.zmpop("My-Sorted-Set", EZGlide.PopScoreFilter.MIN));
            this.logger.info("ZMPopMin: {}", ezGlide.zmpopmin("My-Sorted-Set"));
            this.logger.info("ZMPopMax: {}", ezGlide.zmpopmax("My-Sorted-Set"));
            this.logger.info("ZRandMember: {}", ezGlide.zrandmember("My-Sorted-Set").orElse("My-Sorted-Set not found or is empty"));
            this.logger.info("ZMRem: {}", ezGlide.zrem("My-Sorted-Set", "XXX"));
            this.logger.info("ZMRem: {}", ezGlide.zrem("My-Sorted-Set", List.of("BBB", "CCC")));
            this.logger.info("ZAdd: {}", ezGlide.zadd("My-Sorted-Set", map));
            this.logger.info("ZRemRangeByRank: {}", ezGlide.zremrangebyrank("My-Sorted-Set", 0, 2));
            this.logger.info("ZRemRangeByScore: {}", ezGlide.zremrangebyscore("My-Sorted-Set", 24.0, 26.0));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Connect to Valkey using Glide.
    ///
    /// @return glide.api.GlideClient
    /// @throws java.lang.RuntimeException  When the Glide client cannot be created
    private GlideClient connect() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith());
        }

        final GlideClientConfiguration config =
                GlideClientConfiguration.builder()
                        .address(NodeAddress.builder()
                                .host(this.glideHost)
                                .port(this.glidePort)
                                .build()
                        )
                        .clientName(this.glideClientName)
                        .useTLS(this.glideUseSsl)
                        .build();

        GlideClient glideClient = null;

        try {
            glideClient = GlideClient.createClient(config).exceptionally(throwable -> {
                this.logger.error("Glide client creation incurred an exception: {}", throwable.getMessage(), throwable);
                return null;
            }).get();
        } catch (final ExecutionException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
                this.logger.error("Glide client creation was interrupted: {}", e.getMessage(), e);
            } else {
                this.logger.error("Glide client creation incurred an execution exception: {}", e.getMessage(), e);
            }
        }

        if (glideClient == null) {
            throw new RuntimeException("Unable to create Glide client");
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(glideClient));
        }

        return glideClient;
    }

    /// Cleanup the database.
    ///
    /// @param  ezGlide  net.jmp.spring.boot.valkey.ezglide.EZGlide
    private void cleanup(final EZGlide ezGlide) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(ezGlide));
        }

        this.logger.info("Flush All: {}", ezGlide.flushall());
        this.logger.info("DB size: {}", ezGlide.dbsize());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
