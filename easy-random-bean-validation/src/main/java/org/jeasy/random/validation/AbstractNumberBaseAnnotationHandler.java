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
package org.jeasy.random.validation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.range.*;
import org.jeasy.random.randomizers.text.StringDelegatingRandomizer;

/**
 * @author dadiyang
 * @since 4.3
 */
public abstract class AbstractNumberBaseAnnotationHandler implements BeanValidationAnnotationHandler {

    private final Random random;

    AbstractNumberBaseAnnotationHandler(long seed) {
        random = new Random(seed);
    }

    protected Randomizer<?> getRandomizer(Class<?> fieldType, BigDecimal minValue, BigDecimal maxValue) {
        if (fieldType.equals(Byte.TYPE) || fieldType.equals(Byte.class)) {
            return new ByteRangeRandomizer(
                minValue == null ? null : minValue.byteValue(),
                maxValue == null ? null : maxValue.byteValue(),
                random.nextLong()
            );
        }
        if (fieldType.equals(Short.TYPE) || fieldType.equals(Short.class)) {
            return new ShortRangeRandomizer(
                minValue == null ? null : minValue.shortValue(),
                maxValue == null ? null : maxValue.shortValue(),
                random.nextLong()
            );
        }
        if (fieldType.equals(Integer.TYPE) || fieldType.equals(Integer.class)) {
            return new IntegerRangeRandomizer(
                minValue == null ? null : minValue.intValue(),
                maxValue == null ? null : maxValue.intValue(),
                random.nextLong()
            );
        }
        if (fieldType.equals(Long.TYPE) || fieldType.equals(Long.class)) {
            return new LongRangeRandomizer(
                minValue == null ? null : minValue.longValue(),
                maxValue == null ? null : maxValue.longValue(),
                random.nextLong()
            );
        }
        if (fieldType.equals(BigInteger.class)) {
            return new BigIntegerRangeRandomizer(
                minValue == null ? null : minValue.intValue(),
                maxValue == null ? null : maxValue.intValue(),
                random.nextLong()
            );
        }
        if (fieldType.equals(BigDecimal.class)) {
            return new BigDecimalRangeRandomizer(
                minValue == null ? null : minValue.doubleValue(),
                maxValue == null ? null : maxValue.doubleValue(),
                random.nextLong()
            );
        }
        if (fieldType.equals(String.class)) {
            BigDecimalRangeRandomizer delegate = new BigDecimalRangeRandomizer(
                minValue == null ? null : minValue.doubleValue(),
                maxValue == null ? null : maxValue.doubleValue(),
                random.nextLong()
            );
            return new StringDelegatingRandomizer(delegate);
        }
        return null;
    }
}
