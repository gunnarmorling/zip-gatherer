/*
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Copyright The original authors
 *
 *  Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package dev.morling.demos;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public record ObjToObjZipper<T1, T2, R>(Stream<T2> other, BiFunction<T1, T2, R> zipperFunction) implements Gatherer<T1, Iterator<T2>, R> {

  @Override
  public Supplier<Iterator<T2>> initializer() {
    return () -> other.iterator();
  }

  @Override
  public Integrator<Iterator<T2>, T1, R> integrator() {
    return Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
      if (state.hasNext()) {
        return downstream.push(zipperFunction.apply(element, state.next()));
      }

      return false;
    });
  }
}
