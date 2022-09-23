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

import com.google.protobuf.Message;
import java.lang.reflect.Field;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.annotation.Priority;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerRegistry;

/**
 * A registry of randomizers for Protobuf messages.
 *
 */
@Priority(-2)
public class ProtobufRandomizerRegistry implements RandomizerRegistry {

    private EasyRandom easyRandom;
    private EasyRandomParameters parameters;

    @Override
    public void init(EasyRandomParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public Randomizer<?> getRandomizer(Field field) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Randomizer<?> getRandomizer(Class<?> type) {
        if (Message.class.isAssignableFrom(type)) {
            if (easyRandom == null) {
                easyRandom = new EasyRandom(parameters);
            }
            return new ProtobufMessageRandomizer((Class<Message>) type, easyRandom, parameters);
        }
        if (Message.Builder.class.isAssignableFrom(type)) {
            if (easyRandom == null) {
                easyRandom = new EasyRandom(parameters);
            }
            return new ProtobufMessageBuilderRandomizer((Class<Message.Builder>) type, easyRandom, parameters);
        }
        return null;
    }
}
