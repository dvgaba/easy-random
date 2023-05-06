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

import static org.jeasy.random.util.ReflectionUtils.isArrayType;
import static org.jeasy.random.util.ReflectionUtils.isCollectionType;
import static org.jeasy.random.util.ReflectionUtils.isMapType;
import static org.jeasy.random.util.ReflectionUtils.isOptionalType;

import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import org.jeasy.random.api.RandomizerContext;

/**
 * What is the justification for extending ObjenesisObjectFactory?
 * RecordFactory, to support nesting depth, needs access to context implementation which renders the interface (RandomizerContext)
 * introduced by ObjenesisObjectFactory method signature redundant. Abandoning the inheritance will hence simplify the
 * implementation by reducing the number of parameters, and by additionally storing the instance of RecordFactory as a field in
 * the EasyRandom class.
 */
public class RecordFactory extends ObjenesisObjectFactory {

    private EasyRandom easyRandom;
    private final RandomizationContext contextImpl;

    public RecordFactory(RandomizationContext contextImpl) {
        this.contextImpl = contextImpl;
    }

    @Override
    public <T> T createInstance(Class<T> type, RandomizerContext context) {
        if (easyRandom == null) {
            easyRandom = new EasyRandom(contextImpl.getParameters());
        }
        return createRandomRecord(type, contextImpl);
    }

    private <T> T createRandomRecord(Class<T> recordType, RandomizationContext context) {
        // generate random values for record components
        RecordComponent[] recordComponents = recordType.getRecordComponents();
        Object[] randomValues = new Object[recordComponents.length];

        if (context.hasExceededRandomizationDepth()) {
            for (int i = 0; i < recordComponents.length; i++) {
                Class<?> type = recordComponents[i].getType();
                randomValues[i] = DepthLimitationObjectFactory.produceEmptyValueForField(type);
            }
        } else {
            for (int i = 0; i < recordComponents.length; i++) {
                context.pushStackItem(new RandomizationContextStackItem(recordType, null));
                Class<?> type = recordComponents[i].getType();
                Type genericType = recordComponents[i].getGenericType();
                if (isArrayType(type)) {
                    randomValues[i] = new ArrayPopulator(easyRandom).getRandomArray(type, context);
                } else if (isMapType(type)) {
                    randomValues[i] =
                        new MapPopulator(easyRandom, context.getParameters().getObjectFactory())
                            .getRandomMap(genericType, type, context);
                } else if (isOptionalType(type)) {
                    randomValues[i] = new OptionalPopulator(easyRandom).getRandomOptional(genericType, context);
                } else if (isCollectionType(type)) {
                    randomValues[i] =
                        new CollectionPopulator(easyRandom).getRandomCollection(genericType, type, context);
                } else {
                    randomValues[i] = easyRandom.doPopulateBean(type, context);
                }
                context.popStackItem();
            }
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
            throw new RuntimeException("Invalid record definition", e);
        }
    }
}
