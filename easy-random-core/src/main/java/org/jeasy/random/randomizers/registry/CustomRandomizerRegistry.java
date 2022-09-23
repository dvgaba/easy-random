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
package org.jeasy.random.randomizers.registry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.annotation.Priority;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerRegistry;
import org.jeasy.random.util.ReflectionUtils;

/**
 * Registry of user defined randomizers.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Priority(-1)
public class CustomRandomizerRegistry implements RandomizerRegistry {

    private final Map<BiPredicate<Field, Object>, Randomizer<?>> customFieldRandomizersRegistry = new HashMap<>();
    private final Map<Class<?>, Randomizer<?>> customTypeRandomizersRegistry = new HashMap<>();

    @Override
    public void init(EasyRandomParameters parameters) {
        // no op
    }

    @Override
    public Randomizer<?> getRandomizer(Field field) {
        for (BiPredicate<Field, Object> fieldPredicate : customFieldRandomizersRegistry.keySet()) {
            if (fieldPredicate.test(field, null)) {
                return customFieldRandomizersRegistry.get(fieldPredicate);
            }
        }
        return getRandomizer(field.getType());
    }

    @Override
    public Randomizer<?> getRandomizer(Class<?> type) {
        // issue 241: primitive type were ignored: try to get randomizer by primitive type, if not, then
        // try by wrapper type
        Randomizer<?> randomizer = customTypeRandomizersRegistry.get(type);
        if (randomizer == null) {
            Class<?> wrapperType = type.isPrimitive() ? ReflectionUtils.getWrapperType(type) : type;
            randomizer = customTypeRandomizersRegistry.get(wrapperType);
        }
        return randomizer;
    }

    public <T, R> void registerRandomizer(final Class<T> type, final Randomizer<R> randomizer) {
        customTypeRandomizersRegistry.put(type, randomizer);
    }

    public void registerRandomizer(final BiPredicate<Field, Object> predicate, Randomizer<?> randomizer) {
        customFieldRandomizersRegistry.put(predicate, randomizer);
    }
}
