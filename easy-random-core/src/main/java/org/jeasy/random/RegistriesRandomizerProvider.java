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
package org.jeasy.random;

import java.lang.reflect.Field;
import java.util.*;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerContext;
import org.jeasy.random.api.RandomizerProvider;
import org.jeasy.random.api.RandomizerRegistry;

/**
 * Central class to get registered randomizers by Field or by Type.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class RegistriesRandomizerProvider implements RandomizerProvider {

    private final List<RandomizerRegistry> registries = new ArrayList<>();

    private final Comparator<Object> priorityComparator = new PriorityComparator();

    @Override
    public Randomizer<?> getRandomizerByField(Field field, RandomizerContext context) {
        return getRandomizer(new ByFieldProvider(field));
    }

    @Override
    public <T> Randomizer<T> getRandomizerByType(Class<T> type, RandomizerContext context) {
        return (Randomizer<T>) getRandomizer(new ByTypeProvider(type));
    }

    @Override
    public void setRandomizerRegistries(Set<RandomizerRegistry> randomizerRegistries) {
        this.registries.addAll(randomizerRegistries);
        this.registries.sort(priorityComparator);
    }

    private Randomizer<?> getRandomizer(final Provider provider) {
        return registries
            .stream()
            .map(provider::getRandomizer)
            .filter(Objects::nonNull)
            .sorted(priorityComparator)
            .findFirst()
            .orElse(null);
    }

    @FunctionalInterface
    private interface Provider {
        Randomizer<?> getRandomizer(RandomizerRegistry registry);
    }

    private static class ByTypeProvider implements Provider {

        private final Class<?> type;

        public ByTypeProvider(final Class<?> type) {
            this.type = type;
        }

        @Override
        public Randomizer<?> getRandomizer(final RandomizerRegistry registry) {
            return registry.getRandomizer(type);
        }
    }

    private static class ByFieldProvider implements Provider {

        private final Field field;

        public ByFieldProvider(final Field field) {
            this.field = field;
        }

        @Override
        public Randomizer<?> getRandomizer(final RandomizerRegistry registry) {
            return registry.getRandomizer(field);
        }
    }
}
