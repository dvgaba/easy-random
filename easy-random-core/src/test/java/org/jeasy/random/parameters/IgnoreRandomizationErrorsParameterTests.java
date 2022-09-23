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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.Callable;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.ObjectCreationException;
import org.junit.jupiter.api.Test;

class IgnoreRandomizationErrorsParameterTests {

    private EasyRandom easyRandom;

    @Test
    void whenIgnoreRandomizationErrorsIsActivated_thenShouldReturnNull() {
        EasyRandomParameters parameters = new EasyRandomParameters().ignoreRandomizationErrors(true);
        easyRandom = new EasyRandom(parameters);

        Foo foo = easyRandom.nextObject(Foo.class);

        Assertions.assertThat(foo).isNotNull();
        Assertions.assertThat(foo.getName()).isNotNull();
        Assertions.assertThat(foo.getCallable()).isNull();
    }

    @Test
    void whenIgnoreRandomizationErrorsIsDeactivated_thenShouldThrowObjectGenerationException() {
        EasyRandomParameters parameters = new EasyRandomParameters().ignoreRandomizationErrors(false);
        easyRandom = new EasyRandom(parameters);

        assertThatThrownBy(() -> easyRandom.nextObject(Foo.class)).isInstanceOf(ObjectCreationException.class);
    }

    static class Foo {

        private String name;
        private Callable<String> callable;

        public Foo() {}

        public String getName() {
            return this.name;
        }

        public Callable<String> getCallable() {
            return this.callable;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCallable(Callable<String> callable) {
            this.callable = callable;
        }
    }
}
