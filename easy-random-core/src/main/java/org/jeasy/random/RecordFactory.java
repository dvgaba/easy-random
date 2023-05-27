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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;

import org.jeasy.random.api.RandomizerContext;

/**
 * What is the justification for extending ObjenesisObjectFactory?
 * RecordFactory, to support nesting depth, needs access to context implementation which renders the interface (RandomizerContext)
 * introduced by ObjenesisObjectFactory method signature redundant. Abandoning the inheritance will hence simplify the
 * implementation by reducing the number of parameters, and by additionally storing the instance of RecordFactory as a field in
 * the EasyRandom class.
 */
public class RecordFactory extends ObjenesisObjectFactory {

    private final EasyRandom easyRandom;
    private final RecordFieldPopulator recordFieldPopulator;

    public RecordFactory(EasyRandom easyRandom) {
        this.easyRandom = easyRandom;
        recordFieldPopulator =
                new RecordFieldPopulator(
                        easyRandom,
                        easyRandom.getRandomizerProvider(),
                        new ArrayPopulator(easyRandom),
                        new CollectionPopulator(easyRandom),
                        new MapPopulator(easyRandom, easyRandom.getObjectFactory()),
                        new OptionalPopulator(easyRandom)
                );
    }

    @Override
    public <T> T createInstance(Class<T> type, RandomizerContext context) {
        return createRandomRecord(type, (RandomizationContext) context);
    }

    private <T> boolean isRecord(final Class<T> type) {
        return type.isRecord();
    }

    private <T> T createRandomRecord(Class<T> recordType, RandomizationContext context) {
        // generate random values for record components
        Field[] fields = recordType.getDeclaredFields();
        RecordComponent[] recordComponents = recordType.getRecordComponents();
        Object[] randomValues = new Object[recordComponents.length];

        for (int i = 0; i < recordComponents.length; i++) {
            context.pushStackItem(new RandomizationContextStackItem(recordType, null));
            Class<?> type = recordComponents[i].getType();
            try {
                if (isRecord(type)) {
                    randomValues[i] = easyRandom.doPopulateBean(type, context);
                } else {
                    randomValues[i] = this.recordFieldPopulator.populateField(fields[i], recordType, context);
                }
            } catch (IllegalAccessException e) {
                throw new ObjectCreationException(
                        "Unable to create a random instance of recordType " + recordType,
                        e
                );
            }
            context.popStackItem();
        }

        // create a random instance with random values
        try {
            Constructor<T> canonicalConstructor = getCanonicalConstructor(recordType);
            canonicalConstructor.setAccessible(true);
            return canonicalConstructor.newInstance(randomValues);
        } catch (Exception e) {
            throw new ObjectCreationException("Unable to create a random instance of recordType " + recordType, e);
        }
    }

    private <T> Constructor<T> getCanonicalConstructor(Class<T> recordType) {
        RecordComponent[] recordComponents = recordType.getRecordComponents();
        Class<?>[] componentTypes = new Class<?>[recordComponents.length];
        for (int i = 0; i < recordComponents.length; i++) {
            // recordComponents are ordered, see javadoc:
            // "The components are returned in the same order that they are declared in the record header"
            componentTypes[i] = recordComponents[i].getType();
        }
        try {
            return recordType.getDeclaredConstructor(componentTypes);
        } catch (NoSuchMethodException e) {
            // should not happen, from Record javadoc:
            // "A record class has the following mandated members: a public canonical constructor ,
            // whose descriptor is the same as the record descriptor;"
            throw new ObjectCreationException("Invalid record definition", e);
        }
    }
}
