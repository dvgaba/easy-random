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

import static org.jeasy.random.util.ReflectionUtils.isStatic;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.jeasy.random.api.ExclusionPolicy;
import org.jeasy.random.api.RandomizerContext;

/**
 * Component that encapsulates the logic of field/type exclusion in a given randomization context.
 * This class implements exclusion rules in the predefined order.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class DefaultExclusionPolicy implements ExclusionPolicy {

    /**
     * Given the current randomization context, should the field be excluded from being populated ?
     *
     * @param field the field to check
     * @param context the current randomization context
     * @return true if the field should be excluded, false otherwise
     */
    public boolean shouldBeExcluded(final Field field, final RandomizerContext context) {
        if (isStatic(field)) {
            return true;
        }
        Set<BiPredicate<Field, Object>> fieldExclusionPredicates = context
            .getParameters()
            .getFieldExclusionPredicates();
        for (BiPredicate<Field, Object> fieldExclusionPredicate : fieldExclusionPredicates) {
            if (fieldExclusionPredicate.test(field, null)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Given the current randomization context, should the type be excluded from being populated ?
     *
     * @param type the type to check
     * @param context the current randomization context
     * @return true if the type should be excluded, false otherwise
     */
    public boolean shouldBeExcluded(final Class<?> type, final RandomizerContext context) {
        Set<Predicate<Class<?>>> typeExclusionPredicates = context.getParameters().getTypeExclusionPredicates();
        for (Predicate<Class<?>> typeExclusionPredicate : typeExclusionPredicates) {
            if (typeExclusionPredicate.test(type)) {
                return true;
            }
        }
        return false;
    }
}
