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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.jeasy.random.api.RandomizerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ObjenesisObjectFactoryTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RandomizerContext context;

    private ObjenesisObjectFactory objenesisObjectFactory;

    @BeforeEach
    void setUp() {
        objenesisObjectFactory = new ObjenesisObjectFactory();
    }

    @Test
    void concreteClassesShouldBeCreatedAsExpected() {
        String string = objenesisObjectFactory.createInstance(String.class, context);

        assertThat(string).isNotNull();
    }

    @Test
    void whenNoConcreteTypeIsFound_thenShouldThrowAnInstantiationError() {
        Mockito.when(context.getParameters().isScanClasspathForConcreteTypes()).thenReturn(true);
        assertThatThrownBy(() -> objenesisObjectFactory.createInstance(AbstractFoo.class, context))
            .isInstanceOf(InstantiationError.class);
    }

    private abstract class AbstractFoo {}
}
