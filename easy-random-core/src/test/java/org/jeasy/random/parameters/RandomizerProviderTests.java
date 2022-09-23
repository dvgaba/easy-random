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
package org.jeasy.random.parameters;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Set;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerContext;
import org.jeasy.random.api.RandomizerProvider;
import org.jeasy.random.api.RandomizerRegistry;
import org.junit.jupiter.api.Test;

class RandomizerProviderTests {

    @Test
    void testCustomRandomizerProvider() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters()
            .randomizerProvider(
                new RandomizerProvider() {
                    private Set<RandomizerRegistry> randomizerRegistries;

                    @Override
                    public void setRandomizerRegistries(Set<RandomizerRegistry> randomizerRegistries) {
                        this.randomizerRegistries = randomizerRegistries;
                        // may sort registries with a custom sort algorithm (ie, not necessarily with `@Priority`)
                    }

                    @Override
                    public Randomizer<?> getRandomizerByField(Field field, RandomizerContext context) {
                        // return custom randomizer based on the context
                        if (field.getName().equals("name") && context.getCurrentRandomizationDepth() == 0) {
                            return () -> "foo";
                        }
                        if (field.getName().equals("name") && context.getCurrentField().equals("bestFriend")) {
                            return () -> "bar";
                        }
                        return null;
                    }

                    @Override
                    public <T> Randomizer<T> getRandomizerByType(Class<T> type, RandomizerContext context) {
                        for (RandomizerRegistry randomizerRegistry : randomizerRegistries) {
                            Randomizer<?> randomizer = randomizerRegistry.getRandomizer(type);
                            if (randomizer != null) {
                                return (Randomizer<T>) randomizer;
                            }
                        }
                        return null;
                    }
                }
            )
            .randomizationDepth(2);
        EasyRandom easyRandom = new EasyRandom(parameters);

        // when
        Foo foo = easyRandom.nextObject(Foo.class);

        // then
        assertThat(foo).isNotNull();
        assertThat(foo.getName()).isEqualTo("foo");
        assertThat(foo.getBestFriend().getName()).isEqualTo("bar");
        assertThat(foo.getBestFriend().getBestFriend().getName()).isNull();
    }

    static class Foo {

        private String name;
        private Foo bestFriend;

        public Foo() {}

        public String getName() {
            return this.name;
        }

        public Foo getBestFriend() {
            return this.bestFriend;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setBestFriend(Foo bestFriend) {
            this.bestFriend = bestFriend;
        }
    }
}
