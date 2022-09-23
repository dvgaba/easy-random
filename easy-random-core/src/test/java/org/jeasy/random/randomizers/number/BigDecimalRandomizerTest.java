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
package org.jeasy.random.randomizers.number;

import static org.assertj.core.api.BDDAssertions.then;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.jeasy.random.randomizers.AbstractRandomizerTest;
import org.junit.jupiter.api.Test;

class BigDecimalRandomizerTest extends AbstractRandomizerTest<BigDecimal> {

    @Test
    void generatedValueShouldHaveProvidedPositiveScale() {
        // given
        Integer scale = 1;
        BigDecimalRandomizer bigDecimalRandomizer = new BigDecimalRandomizer(scale);

        // when
        BigDecimal bigDecimal = bigDecimalRandomizer.getRandomValue();

        then(bigDecimal.scale()).isEqualTo(scale);
    }

    @Test
    void generatedValueShouldHaveProvidedNegativeScale() {
        // given
        Integer scale = -1;
        BigDecimalRandomizer bigDecimalRangeRandomizer = new BigDecimalRandomizer(scale);

        // when
        BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

        then(bigDecimal.scale()).isEqualTo(scale);
    }

    @Test
    void testCustomRoundingMode() {
        // given
        long initialSeed = 123;
        Integer scale = 1;
        RoundingMode roundingMode = RoundingMode.DOWN;
        BigDecimalRandomizer bigDecimalRandomizer = new BigDecimalRandomizer(initialSeed, scale, roundingMode);

        // when
        BigDecimal bigDecimal = bigDecimalRandomizer.getRandomValue();

        then(bigDecimal).isEqualTo(new BigDecimal("0.7"));
    }
}
