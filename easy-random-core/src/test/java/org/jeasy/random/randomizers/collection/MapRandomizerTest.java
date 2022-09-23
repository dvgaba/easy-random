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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.jeasy.random.api.Randomizer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MapRandomizerTest {

    @Mock
    private Randomizer<Integer> keyRandomizer;

    @Mock
    private Randomizer<String> valueRandomizer;

    @Test
    void generatedMapShouldNotBeEmpty() {
        assertThat(new MapRandomizer(keyRandomizer, valueRandomizer).getRandomValue()).isNotEmpty();
    }

    @Test
    void generatedMapSizeShouldBeEqualToTheSpecifiedSize() {
        when(keyRandomizer.getRandomValue()).thenReturn(1, 2, 3);
        assertThat(new MapRandomizer(keyRandomizer, valueRandomizer, 3).getRandomValue()).hasSize(3);
    }

    @Test
    void specifiedSizeCanBeZero() {
        assertThat(new MapRandomizer(keyRandomizer, valueRandomizer, 0).getRandomValue()).isEmpty();
    }

    @Test
    void specifiedSizeShouldBePositive() {
        assertThatThrownBy(() -> new MapRandomizer(keyRandomizer, valueRandomizer, -3))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nullKeyRandomizer() {
        assertThatThrownBy(() -> new MapRandomizer(null, valueRandomizer, 3))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nullValueRandomizer() {
        assertThatThrownBy(() -> new MapRandomizer(keyRandomizer, null, 3))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
