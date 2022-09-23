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

    private static EnumMap<Type, Class<?>> protoToJavaTypeMap = new EnumMap<>(FieldDescriptor.Type.class);

    static {
        protoToJavaTypeMap.put(Type.INT32, Int32Value.class);
        protoToJavaTypeMap.put(Type.INT64, Int64Value.class);
        protoToJavaTypeMap.put(Type.BOOL, BoolValue.class);
        protoToJavaTypeMap.put(Type.BYTES, BytesValue.class);
        protoToJavaTypeMap.put(Type.DOUBLE, DoubleValue.class);
        protoToJavaTypeMap.put(Type.FIXED32, Int32Value.class);
        protoToJavaTypeMap.put(Type.FIXED64, Int64Value.class);
        protoToJavaTypeMap.put(Type.SFIXED32, Int32Value.class);
        protoToJavaTypeMap.put(Type.SFIXED64, Int64Value.class);
        protoToJavaTypeMap.put(Type.UINT32, Int64Value.class);
        protoToJavaTypeMap.put(Type.UINT64, Int32Value.class);
        protoToJavaTypeMap.put(Type.FLOAT, FloatValue.class);
        protoToJavaTypeMap.put(Type.STRING, StringValue.class);
    }

    public static BiPredicate<Field, Object> named(final String name) {
        final Pattern pattern = Pattern.compile(name + "_");
        return (field, fieldDescriptor) -> pattern.matcher(field.getName()).matches();
    }

    public static BiPredicate<Field, Object> ofProtobufType(Class<?> type) {
        return (field, fieldDescriptor) -> {
            FieldDescriptor descriptor = (FieldDescriptor) fieldDescriptor;
            if (descriptor.getType() == Type.ENUM) {
                return descriptor.getEnumType().getFullName().equals(type.getSimpleName());
            }
            return Optional
                .ofNullable(protoToJavaTypeMap.get(descriptor.getType()))
                .filter(t -> t.equals(type))
                .isPresent();
        };
    }

    public static Predicate<Class<?>> ofType(Class<?> type) {
        return clz -> clz.equals(type);
    }
}
