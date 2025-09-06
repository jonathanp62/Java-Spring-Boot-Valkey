package net.jmp.spring.boot.valkey;

/*
 * (#)SailService.java  0.5.0   08/04/2025
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

import net.jmp.spring.boot.valkey.sail.*;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

/// The Sail service class.
///
/// @version    0.5.0
/// @since      0.5.0
@Service
public class SailService {
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
    @Value("${glide.flushDbOnSailServiceStart}")
    private boolean flushDbOnServiceStart;

    /// Flush the database at the end when true.
    @Value("${glide.flushDbOnSailServiceStop}")
    private boolean flushDbOnServiceStop;

    /// The default constructor.
    public SailService() {
        super();
    }

    /// The demo method.
    public void demo() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final SailConfig sailConfig = SailConfig.builder()
            .clientName(this.glideClientName)
            .hostName(this.glideHost)
            .port(this.glidePort)
            .useSSL(this.glideUseSsl)
            .build();

        this.logger.info("Sail config: {}", sailConfig);

        try (final Sail sail = new Sail(sailConfig)) {
            this.sString(sail);
            this.sServer(sail);
            this.sClient(sail);
            this.sHash(sail);
            this.sList(sail);
            this.sSet(sail);
            this.sSetIntersections(sail);
            this.sSortedSet(sail);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Demonstrate the SString class.
    ///
    /// @param  sail    net.jmp.spring.boot.valkey.sail.Sail
    private void sString(final Sail sail) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(sail));
        }

        final SString string = sail.newString("Demo");

        string.set("Sail 0.5.0");
        string.append(" - Coming soon");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("STRING: {}: {}", string.getName(), string.get().orElse("No value found for key \"Demo\""));
            this.logger.info("STRING: {}: {}", string.getName(), string.length());
            this.logger.info("STRING: {}: {}", string.getName(), string.rename("Renamed"));
            this.logger.info("STRING: {}: {}", string.getName(), string.getThenDelete().orElse("No value found for key \"Renamed\""));
            this.logger.info("STRING: {}: {}", string.getName(), string.exists());
        }

        string.set("New value");

        this.logger.info("STRING: {}: {}", string.getName(), string.delete());

        string.set("Sail 0.5.0");
        string.setIfAbsent("Sail 0.5.1");

        this.logger.info("STRING: {}: {}", string.getName(), string.substr(0, 3));
        this.logger.info("STRING: {}: {}", string.getName(), string.setRange(5, "into the future"));

        string.copy("Copied").ifPresent(copiedString -> {
            this.logger.info("STRING: {}: {}", copiedString.getName(), copiedString.get().orElse("No value found for key \"Copied\""));
        });

        string.move("Moved").ifPresent(movedString -> {
            this.logger.info("STRING: {}: {}", movedString.getName(), movedString.get().orElse("No value found for key \"Moved\""));
        });

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Demonstrate the SServer class.
    ///
    /// @param  sail    net.jmp.spring.boot.valkey.sail.Sail
    private void sServer(final Sail sail) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(sail));
        }

        final SServer server = sail.newServer();

        if (this.logger.isInfoEnabled()) {
            this.logger.info("SERVER: {}", server.ping());
            this.logger.info("SERVER: {}", server.ping("Hello"));
            this.logger.info("SERVER: {}", server.echo("World"));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Demonstrate the SClient class.
    ///
    /// @param  sail    net.jmp.spring.boot.valkey.sail.Sail
    private void sClient(final Sail sail) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(sail));
        }

        final SClient client = sail.newClient();

        if (this.logger.isInfoEnabled()) {
            this.logger.info("CLIENT: {}", client.name());
            this.logger.info("CLIENT: {}", client.id());
            this.logger.info("CLIENT: {}", client.info());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Demonstrate the SHash class.
    ///
    /// @param  sail    net.jmp.spring.boot.valkey.sail.Sail
    private void sHash(final Sail sail) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(sail));
        }

        final Map<String, String> map = Map.of(
                "firstName", "Laura",
                "middleName", "Anne",
                "lastName", "Ashe"
        );

        final Map<String, String> extraMappings = Map.of(
                "firstHusband", "John",
                "secondHusband", "Rob"
        );

        final SHash hash = sail.newHash("Third");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("HASH: {}", hash.set(map));
            this.logger.info("HASH: {}", hash.keys());
            this.logger.info("HASH: {}", hash.values());
            this.logger.info("HASH: {}", hash.size());
            this.logger.info("HASH: {}", hash.valueLength("middleName"));
            this.logger.info("HASH: {}", hash.get("middleName").orElse("No value found for key \"middleName\""));
            this.logger.info("HASH: {}", hash.get("nickName").orElse("No value found for key \"nickName\""));
            this.logger.info("HASH: {}", hash.containsKey("middleName"));
            this.logger.info("HASH: {}", hash.containsKey("nickName"));
            this.logger.info("HASH: {}", hash.isEmpty());
            this.logger.info("HASH: {}", hash.containsValue("Laura"));
            this.logger.info("HASH: {}", hash.put("nickName", "Bitch"));
            this.logger.info("HASH: {}", hash.putIfAbsent("nickName", "Liar"));
            this.logger.info("HASH: {}", hash.remove("nickName"));
            this.logger.info("HASH: {}", hash.randomEntryKey().orElse("No entry key found"));
        }

        hash.putAll(extraMappings);

        hash.copy("CopiedHash").ifPresent(copiedHash -> {
            this.logger.info("HASH: {}", copiedHash.getName());
            copiedHash.clear();
        });

        hash.move("MovedHash").ifPresent(movedHash -> {
            this.logger.info("HASH: {}", movedHash.getName());
            this.logger.info("HASH: {}", movedHash.toMap());
        });
        
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Demonstrate the SList class.
    ///
    /// @param  sail    net.jmp.spring.boot.valkey.sail.Sail
    private void sList(final Sail sail) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(sail));
        }

        final List<String> composers = List.of("Bach", "Beethoven", "Brahms");
        final SList list = sail.newList("Composers");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("LIST: {}", list.exists());    // Will be false until an element is added
            this.logger.info("LIST: {}", list.addFirstIfExists("Beethoven"));
            this.logger.info("LIST: {}", list.addLastIfExists("Zemlinsky"));
        }

        list.addAll(composers);

        if (this.logger.isInfoEnabled()) {
            this.logger.info("LIST: {}", list.size());
            this.logger.info("LIST: {}", list.get(0).orElse("No value found for index 0"));
            this.logger.info("LIST: {}", list.insertBefore("Bach", "Albeniz"));
            this.logger.info("LIST: {}", list.insertAfter("Brahms", "Chopin"));
            this.logger.info("LIST: {}", list.indexOf("Beethoven"));
        }

        list.addFirst("Adams");
        list.addLast("Tchaikovsky");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("LIST: {}", list.subList(2, 5));
            this.logger.info("LIST: {}", list.removeFirst());
            this.logger.info("LIST: {}", list.removeLast());
            this.logger.info("LIST: {}", list.remove("Brahms"));
        }

        list.trimToRange(1, 2);

        if (this.logger.isInfoEnabled()) {
            this.logger.info("LIST: {}", list.set(1, "Brahms"));
        }

        final SList source = sail.newList("Source");
        final SList target = sail.newList("Target");

        source.addAll(List.of("A", "B", "C", "D", "E"));

        if (this.logger.isInfoEnabled()) {
            this.logger.info("LIST: {}", list.contains("Brahms"));
            this.logger.info("LIST: {}", list.contains("Bach"));
            this.logger.info("LIST: {}", source.containsAll(List.of("A", "B", "C", "D", "E")));

            // A moves to target (A)
            this.logger.info("LIST: {}", source.moveFirstToListFirst(target).orElse("No value found for key \"Source\""));
            // B moves to target (A B)
            this.logger.info("LIST: {}", source.moveFirstToListLast(target).orElse("No value found for key \"Source\""));
            // E moves to target (E A B)
            this.logger.info("LIST: {}", source.moveLastToListFirst(target).orElse("No value found for key \"Source\""));
            // D moves to target (E A B D)
            this.logger.info("LIST: {}", source.moveLastToListLast(target).orElse("No value found for key \"Source\""));
            this.logger.info("LIST: {}", source.containsAll(List.of("A", "B", "C", "D", "E")));
        }

        final SList reversed = target.reversed("Reversed");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("LIST: {}", reversed.retainAll(List.of("D", "E")));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Demonstrate the SSet class.
    ///
    /// @param  sail    net.jmp.spring.boot.valkey.sail.Sail
    private void sSet(final Sail sail) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(sail));
        }

        final SSet set = sail.newSet("Colors");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("SET: {}", set.isEmpty());
            this.logger.info("SET: {}", set.add("Red"));
            this.logger.info("SET: {}", set.addAll(List.of("Orange", "Yellow")));
            this.logger.info("SET: {}", set.size());
            this.logger.info("SET: {}", set.isEmpty());
            this.logger.info("SET: {}", set.contains("Green"));
            this.logger.info("SET: {}", set.containsAll(List.of("Red", "Orange", "Yellow")));
            this.logger.info("SET: {}", set.members());
            this.logger.info("SET: {}", set.remove("Green"));
            this.logger.info("SET: {}", set.getAndRemoveRandomMember().orElse("No value found"));
            this.logger.info("SET: {}", set.getAndRemoveRandomMembers(3));
        }

        final SSet colors = sail.newSet("Colors");
        final SSet green = sail.newSet("Green");

        colors.addAll(List.of("Red", "Orange", "Yellow"));
        colors.add("Green");
        colors.addAll(List.of("Blue", "Indigo", "Violet"));

        if (this.logger.isInfoEnabled()) {
            this.logger.info("SET: {}", set.moveToSet("Green", green));
            this.logger.info("SET: {}", set.getRandomMembers(2));
            this.logger.info("SET: {}", set.getRandomMember().orElse("No value found"));
            this.logger.info("SET: {}", set.retainAll(List.of("Red", "Orange", "Yellow")));
            this.logger.info("SET: {}", set.removeAll(List.of("Red", "Orange")));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Demonstrate the SSet intersections.
    ///
    /// @param  sail    net.jmp.spring.boot.valkey.sail.Sail
    private void sSetIntersections(final Sail sail) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(sail));
        }

        final SSet fruits = sail.newSet("Fruits");
        final SSet vegetables = sail.newSet("Vegetables");

        fruits.addAll(List.of("Apple", "Banana", "Cherry", "Date", "Tomato"));
        vegetables.addAll(List.of("Asparagus", "Broccoli", "Carrot", "Tomato", "Turnip"));

        final SSet diff = sail.newSet("Diff-Fruits&Vegetables");
        final SSet intersection = sail.newSet("Intersection-Fruits&Vegetables");
        final SSet union = sail.newSet("Union-Fruits&Vegetables");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("SET: {}", fruits.diff(vegetables));       // Apple, Banana, Cherry, Date
            this.logger.info("SET: {}", fruits.diffAndStore(diff, vegetables));
            this.logger.info("SET: {}", fruits.intersect(vegetables));  // Tomato
            this.logger.info("SET: {}", fruits.intersectSize(vegetables));
            this.logger.info("SET: {}", fruits.intersectAndStore(intersection, vegetables));
            this.logger.info("SET: {}", fruits.union(vegetables));
            this.logger.info("SET: {}", fruits.unionAndStore(union, vegetables));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Demonstrate the SSortedSet class.
    ///
    /// @param  sail    net.jmp.spring.boot.valkey.sail.Sail
    private void sSortedSet(final Sail sail) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(sail));
        }

        final SSortedSet sortedSet = sail.newSortedSet("Scored-Letters");

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
            this.logger.info("SORTEDSET: {}", sortedSet.addAll(map));
            this.logger.info("SORTEDSET: {}", sortedSet.add("WWW", 4.0));
            this.logger.info("SORTEDSET: {}", sortedSet.size());
            this.logger.info("SORTEDSET: {}", sortedSet.score("CCC"));
            this.logger.info("SORTEDSET: {}", sortedSet.rank("CCC"));
            this.logger.info("SORTEDSET: {}", sortedSet.reversedRank("CCC"));
            this.logger.info("SORTEDSET: {}", sortedSet.count(1.0, 3.0));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
