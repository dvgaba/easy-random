package org.jeasy.random.protobuf;

import com.google.protobuf.Descriptors.FieldDescriptor;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.BiPredicate;
import org.jeasy.random.DefaultExclusionPolicy;
import org.jeasy.random.api.RandomizerContext;

public class ProtobufExclusionPolicy extends DefaultExclusionPolicy {

    @Override
    public boolean shouldBeExcluded(final Field field, final RandomizerContext context, Object object) {
        if (!(object instanceof FieldDescriptor)) {
            throw new IllegalArgumentException("3rd parameter must be of FieldDescriptor");
        }
        FieldDescriptor fieldDescriptor = (FieldDescriptor) object;
        Set<BiPredicate<Field, Object>> fieldExclusionPredicates = context
            .getParameters()
            .getFieldExclusionPredicates();
        for (BiPredicate<Field, Object> fieldExclusionPredicate : fieldExclusionPredicates) {
            if (fieldExclusionPredicate.test(field, fieldDescriptor)) {
                return true;
            }
        }
        return false;
    }
}
