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
package org.jeasy.random.randomizers.time.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jeasy.random.randomizers.time.HourRandomizer.MAX_HOUR;
import static org.jeasy.random.randomizers.time.HourRandomizer.MIN_HOUR;

import org.jeasy.random.randomizers.AbstractRandomizerTest;
import org.jeasy.random.randomizers.time.HourRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HourRandomizerTest extends AbstractRandomizerTest<Integer> {

    @BeforeEach
    void setUp() {
        randomizer = new HourRandomizer();
    }

    @Test
    void generatedValueShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    void generatedValueShouldBeWithinRange() {
        assertThat(randomizer.getRandomValue()).isBetween(MIN_HOUR, MAX_HOUR);
    }

    @Test
    void shouldGenerateTheSameValueForTheSameSeed() {
        // Given
        randomizer = new HourRandomizer(SEED);
        Integer expected = 16;

        // When
        Integer actual = randomizer.getRandomValue();

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
