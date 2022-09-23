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
package org.jeasy.random.protobuf;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.protobuf.ByteString;
import org.junit.jupiter.api.Test;

class ByteStringRandomizerTest {

    private static final long SEED = 123L;

    @Test
    void generatedByteStringShouldNotBeNull() {
        ByteString generatedValue = new ByteStringRandomizer().getRandomValue();
        assertThat(generatedValue).isNotNull();
    }

    @Test
    void shouldGenerateTheSameValueForTheSameSeed() {
        int[] expectedByteArray = {
            -35,
            -15,
            33,
            -71,
            -107,
            4,
            -68,
            60,
            -47,
            -116,
            -85,
            -3,
            -86,
            -16,
            51,
            77,
            22,
            -47,
            -41,
            64,
            50,
            38,
            -6,
            -110,
            69,
            87,
            -38,
            -101,
            58,
            15,
            70,
            66,
        };
        ByteString generatedValue = new ByteStringRandomizer(SEED).getRandomValue();
        assertThat(generatedValue.toByteArray()).containsExactly(expectedByteArray);
    }
}
