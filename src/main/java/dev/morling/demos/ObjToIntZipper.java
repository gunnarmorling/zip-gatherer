/*
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Copyright The original authors
 *
 *  Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package dev.morling.demos;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.IntStream;

import dev.morling.demos.ObjToIntZipper.ZipperState;

public record ObjToIntZipper<T1, R>(IntStream other, ObjIntBiFunction<T1, R> zipperFunction) implements Gatherer<T1, ZipperState, R> {

	@Override
	public Supplier<ZipperState> initializer() {
		return () -> new ZipperState(other.spliterator());
	}

	@Override
	public Integrator<ZipperState, T1, R> integrator() {
		return Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
			AtomicInteger otherElement = new AtomicInteger();
			if(!state.other().tryAdvance((IntConsumer)(e -> otherElement.set(e)))) {
				return false;
			}
			return downstream.push(zipperFunction.apply(element, otherElement.get()));
		});
	}

    static record ZipperState(Spliterator.OfInt other) {
    }

    @FunctionalInterface
    public static interface ObjIntBiFunction<T1, R> {
    	R apply(T1 a, int b);
    }

}