/*
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Copyright The original authors
 *
 *  Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package dev.morling.demos;

import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import dev.morling.demos.ObjToIntZipper.ObjIntBiFunction;

public class Zippers {

    public static <T2> ObjToObjZipperBuilder<T2> zip(Stream<T2> other) {
        return new ObjToObjZipperBuilder<>(other);
    }

    public static <T2> ObjToIntZipperBuilder zip(IntStream other) {
        return new ObjToIntZipperBuilder(other);
    }

    public static record ObjToObjZipperBuilder<T2>(Stream<T2> other)
    {

    <T1, R> ObjToObjZipper<T1, T2, R> with(BiFunction<T1, T2, R> zipperFunction) {
    		return new ObjToObjZipper<>(other, zipperFunction);
    	}

    }

    public static record ObjToIntZipperBuilder(IntStream other) {

    <T1, R> ObjToIntZipper<T1, R> with(ObjIntBiFunction<T1, R> zipperFunction) {
        return new ObjToIntZipper<>(other, zipperFunction);
    }
}}
