/*
 *  Copyright © 2020 Aurélien Mino (aurelien.mino@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  =====================================================================
 *  Notice: Files are restructured and modified
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
