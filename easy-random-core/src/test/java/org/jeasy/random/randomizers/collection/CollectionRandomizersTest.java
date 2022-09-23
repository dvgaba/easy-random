/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.random.randomizers.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.jeasy.random.api.Randomizer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CollectionRandomizersTest {

    private static final int collectionSize = 3;

    static Object[] generateCollectionRandomizers() {
        Randomizer<String> elementRandomizer = elementRandomizer();
        return new Object[] {
            new ListRandomizer(elementRandomizer),
            new QueueRandomizer(elementRandomizer),
            new SetRandomizer(elementRandomizer),
        };
    }

    @ParameterizedTest
    @MethodSource("generateCollectionRandomizers")
    <T> void generatedCollectionShouldNotBeNull(Randomizer<Collection<T>> collectionRandomizer) {
        // when
        Collection<T> randomCollection = collectionRandomizer.getRandomValue();

        then(randomCollection).isNotNull();
    }

    static Object[] generateCollectionRandomizersWithSpecificSize() {
        return new Object[] {
            new ListRandomizer(elementRandomizer(), collectionSize),
            new QueueRandomizer(elementRandomizer(), collectionSize),
            new SetRandomizer(elementRandomizer(), collectionSize),
        };
    }

    @ParameterizedTest
    @MethodSource("generateCollectionRandomizersWithSpecificSize")
    <T> void generatedCollectionSizeShouldBeEqualToTheSpecifiedSize(Randomizer<Collection<T>> collectionRandomizer) {
        // when
        Collection<T> randomCollection = collectionRandomizer.getRandomValue();

        then(randomCollection).hasSize(collectionSize);
    }

    static Object[] generateCollectionRandomizersForEmptyCollections() {
        return new Object[] {
            new ListRandomizer(elementRandomizer(), 0),
            new QueueRandomizer(elementRandomizer(), 0),
            new SetRandomizer(elementRandomizer(), 0),
        };
    }

    @ParameterizedTest
    @MethodSource("generateCollectionRandomizersForEmptyCollections")
    <T> void shouldAllowGeneratingEmptyCollections(Randomizer<Collection<T>> collectionRandomizer) {
        // when
        Collection<T> randomCollection = collectionRandomizer.getRandomValue();

        then(randomCollection).isEmpty();
    }

    static Object[] generateCollectionRandomizersWithIllegalSize() {
        Randomizer<String> elementRandomizer = elementRandomizer();
        int illegalSize = -1;
        return new Object[] {
            (ThrowingCallable) () -> new ListRandomizer(elementRandomizer, illegalSize),
            (ThrowingCallable) () -> new QueueRandomizer(elementRandomizer, illegalSize),
            (ThrowingCallable) () -> new SetRandomizer(elementRandomizer, illegalSize),
        };
    }

    @ParameterizedTest
    @MethodSource("generateCollectionRandomizersWithIllegalSize")
    void specifiedSizeShouldBePositive(ThrowingCallable callable) {
        // when
        Throwable expectedException = catchThrowable(callable);

        // then
        assertThat(expectedException).isInstanceOf(IllegalArgumentException.class);
    }

    @SuppressWarnings("unchecked")
    static Randomizer<String> elementRandomizer() {
        Randomizer<String> elementRandomizer = mock(Randomizer.class);
        when(elementRandomizer.getRandomValue()).thenReturn("a", "b", "c");
        return elementRandomizer;
    }
}
