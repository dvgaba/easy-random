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

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.Randomizer;

class PastAnnotationHandler implements BeanValidationAnnotationHandler {

    private EasyRandom easyRandom;
    private EasyRandomParameters parameters;

    PastAnnotationHandler(EasyRandomParameters parameters) {
        this.parameters = parameters.copy();
    }

    @Override
    public Randomizer<?> getRandomizer(Field field) {
        if (easyRandom == null) {
            LocalDate now = LocalDate.now();
            parameters.setDateRange(
                new EasyRandomParameters.Range<>(
                    now.minusYears(EasyRandomParameters.DEFAULT_DATE_RANGE),
                    now.minus(1, ChronoUnit.DAYS)
                )
            );
            easyRandom = new EasyRandom(parameters);
        }
        return () -> easyRandom.nextObject(field.getType());
    }
}
