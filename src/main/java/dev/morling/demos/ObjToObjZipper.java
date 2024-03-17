/*
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Copyright The original authors
 *
 *  Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package dev.morling.demos;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

import dev.morling.demos.ObjToObjZipper.ZipperState;

public record ObjToObjZipper<T1, T2, R>(Stream<T2> other, BiFunction<T1, T2, R> zipperFunction) implements Gatherer<T1, ZipperState<T2>, R> {

	@Override
	public Supplier<ZipperState<T2>> initializer() {
		return () -> new ZipperState<>(other.spliterator());
	}

	@Override
	public Integrator<ZipperState<T2>, T1, R> integrator() {
		return Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
			AtomicReference<T2> otherElement = new AtomicReference<>();
			if(!state.other().tryAdvance(e -> otherElement.set(e))) {
				return false;
			}
			return downstream.push(zipperFunction.apply(element, otherElement.get()));
		});
	}

    static record ZipperState<T>(Spliterator<T> other) {
    }
}