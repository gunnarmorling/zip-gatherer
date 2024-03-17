/*
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Copyright The original authors
 *
 *  Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package dev.morling.demos;

import static dev.morling.demos.Zippers.zip;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class ZipperTest {

    @Test
    public void canZipTwoObjectStreams() {
        List<String> letters = List.of("a", "b", "c", "d", "e");
        Stream<Integer> numbers = IntStream.range(0, letters.size()).mapToObj(i -> i);

        List<String> zipped = letters.stream()
                .gather(zip(numbers).with((letter, i) -> i + "-" + letter))
                .collect(Collectors.toList());

        assertThat(zipped).containsExactly("0-a", "1-b", "2-c", "3-d", "4-e");
    }

    @Test
    public void outputHasLengthOfShortestInput() {
        List<String> letters = List.of("a", "b", "c", "d", "e");
        Stream<Integer> numbers = IntStream.range(0, 3).mapToObj(i -> i);

        List<String> zipped = letters.stream()
                .gather(zip(numbers).with((letter, i) -> i + "-" + letter))
                .collect(Collectors.toList());

        assertThat(zipped).containsExactly("0-a", "1-b", "2-c");

        letters = List.of("a", "b");
        numbers = IntStream.range(0, 5).mapToObj(i -> i);

        zipped = letters.stream()
                .gather(zip(numbers).with((letter, i) -> i + "-" + letter))
                .collect(Collectors.toList());

        assertThat(zipped).containsExactly("0-a", "1-b");
    }

    @Test
    public void canZipObjectWithIntStream() {
        List<String> letters = List.of("a", "b", "c", "d", "e");
        IntStream numbers = IntStream.range(0, letters.size());

        List<String> zipped = letters.stream()
                .gather(zip(numbers).with((letter, i) -> i + "-" + letter))
                .collect(Collectors.toList());

        assertThat(zipped).containsExactly("0-a", "1-b", "2-c", "3-d", "4-e");
    }
}
