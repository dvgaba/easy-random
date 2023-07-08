package org.jeasy.random.protobuf;

import java.lang.reflect.Field;
import java.util.function.BiPredicate;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.registry.CustomRandomizerRegistry;

public class ProtobufCustomRandomizerRegistry extends CustomRandomizerRegistry {

    @Override
    public Randomizer<?> getRandomizer(Field field, Object additionalData) {
        for (BiPredicate<Field, Object> fieldPredicate : customFieldRandomizersRegistry.keySet()) {
            if (fieldPredicate.test(field, additionalData)) {
                return customFieldRandomizersRegistry.get(fieldPredicate);
            }
        }
        return null;
    }
}
