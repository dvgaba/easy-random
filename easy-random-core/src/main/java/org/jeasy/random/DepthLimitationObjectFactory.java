package org.jeasy.random;

import java.lang.reflect.Array;
import org.jeasy.random.util.ReflectionUtils;

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
