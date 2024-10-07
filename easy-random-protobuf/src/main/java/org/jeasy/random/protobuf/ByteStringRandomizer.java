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

import com.google.protobuf.ByteString;
import java.util.Random;
import org.jeasy.random.api.Randomizer;

/** Generate a random Protobuf {@link ByteString}. */
public class ByteStringRandomizer implements Randomizer<ByteString> {

    private final Random random;

    public ByteStringRandomizer() {
        this.random = new Random();
    }

    public ByteStringRandomizer(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public ByteString getRandomValue() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return ByteString.copyFrom(bytes);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
