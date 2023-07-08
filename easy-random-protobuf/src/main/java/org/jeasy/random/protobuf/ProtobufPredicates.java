package org.jeasy.random.protobuf;

import com.google.protobuf.BoolValue;
import com.google.protobuf.BytesValue;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.FloatValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ProtobufPredicates {

    private static final EnumMap<FieldDescriptor.Type, Class<?>> PROTO_TO_JAVA_TYPE_MAP = new EnumMap<>(FieldDescriptor.Type.class);
    public static final String PROTO_FIELD_SEPERATOR = "_";

    static {
        PROTO_TO_JAVA_TYPE_MAP.put(Type.INT32, Int32Value.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.INT64, Int64Value.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.BOOL, BoolValue.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.BYTES, BytesValue.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.DOUBLE, DoubleValue.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.FIXED32, Int32Value.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.FIXED64, Int64Value.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.SFIXED32, Int32Value.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.SFIXED64, Int64Value.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.UINT32, Int64Value.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.UINT64, Int32Value.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.FLOAT, FloatValue.class);
        PROTO_TO_JAVA_TYPE_MAP.put(Type.STRING, StringValue.class);
    }

    public static BiPredicate<Field, Object> named(final String name) {
        final Pattern pattern = Pattern.compile(name + PROTO_FIELD_SEPERATOR);
        return (field, fieldDescriptor) -> pattern.matcher(field.getName()).matches();
    }

    public static BiPredicate<Field, Object> ofProtobufType(Class<?> type) {
        return (field, fieldDescriptor) -> {
            FieldDescriptor descriptor = (FieldDescriptor) fieldDescriptor;
            if (descriptor.getType() == Type.ENUM) {
                return descriptor.getEnumType().getFullName().equals(type.getSimpleName());
            }
            return Optional
                .ofNullable(PROTO_TO_JAVA_TYPE_MAP.get(descriptor.getType()))
                .filter(t -> t.equals(type))
                .isPresent();
        };
    }

    public static Predicate<Class<?>> ofType(Class<?> type) {
        return clz -> clz.equals(type);
    }
}
