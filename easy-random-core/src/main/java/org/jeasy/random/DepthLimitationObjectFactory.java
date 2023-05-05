package org.jeasy.random;

import org.jeasy.random.util.ReflectionUtils;

import java.lang.reflect.Array;

class DepthLimitationObjectFactory {
    static Object produceEmptyValueForField(Class<?> fieldType) {
        if (ReflectionUtils.isArrayType(fieldType)) {
            return Array.newInstance(fieldType.getComponentType(), 0);
        }
        if (ReflectionUtils.isCollectionType(fieldType)) {
            return ReflectionUtils.getEmptyImplementationForCollectionInterface(fieldType);
        }
        if (ReflectionUtils.isMapType(fieldType)) {
            return ReflectionUtils.getEmptyImplementationForMapInterface(fieldType);
        }
        return null;
    }
}
