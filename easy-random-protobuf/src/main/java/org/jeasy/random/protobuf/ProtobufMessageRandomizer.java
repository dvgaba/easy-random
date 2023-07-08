/*
 *  Copyright © 2020 Aurélien Mino (aurelien.mino@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  =====================================================================
 *  Notice: Files are restructured and modified
 */
package org.jeasy.random.protobuf;

import static com.google.protobuf.Descriptors.FieldDescriptor.JavaType.BOOLEAN;
import static com.google.protobuf.Descriptors.FieldDescriptor.JavaType.BYTE_STRING;
import static com.google.protobuf.Descriptors.FieldDescriptor.JavaType.DOUBLE;
import static com.google.protobuf.Descriptors.FieldDescriptor.JavaType.ENUM;
import static com.google.protobuf.Descriptors.FieldDescriptor.JavaType.FLOAT;
import static com.google.protobuf.Descriptors.FieldDescriptor.JavaType.INT;
import static com.google.protobuf.Descriptors.FieldDescriptor.JavaType.LONG;
import static com.google.protobuf.Descriptors.FieldDescriptor.JavaType.MESSAGE;
import static com.google.protobuf.Descriptors.FieldDescriptor.JavaType.STRING;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Message;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BiFunction;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.ContextAwareRandomizer;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerContext;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;
import org.jeasy.random.randomizers.text.StringRandomizer;

/** Generate a random Protobuf {@link Message}. */
public class ProtobufMessageRandomizer implements ContextAwareRandomizer<Message> {

    private final Class<Message> messageClass;
    private final EnumMap<JavaType, BiFunction<FieldDescriptor, Message.Builder, Object>> fieldGenerators;
    private final EasyRandom easyRandom;
    private final EasyRandomParameters parameters;
    private RandomizerContext context;

    public ProtobufMessageRandomizer(
        Class<Message> messageClass,
        EasyRandom easyRandom,
        EasyRandomParameters parameters
    ) {
        this.messageClass = messageClass;
        this.easyRandom = easyRandom;
        this.parameters = parameters;

        this.fieldGenerators = new EnumMap<>(JavaType.class);
        this.fieldGenerators.put(INT, (field, containingBuilder) -> easyRandom.nextInt());
        this.fieldGenerators.put(LONG, (field, containingBuilder) -> easyRandom.nextLong());
        this.fieldGenerators.put(FLOAT, (field, containingBuilder) -> easyRandom.nextFloat());
        this.fieldGenerators.put(DOUBLE, (field, containingBuilder) -> easyRandom.nextDouble());
        this.fieldGenerators.put(BOOLEAN, (field, containingBuilder) -> easyRandom.nextBoolean());
        this.fieldGenerators.put(
                STRING,
                (field, containingBuilder) -> new StringRandomizer(easyRandom.nextLong()).getRandomValue()
            );
        this.fieldGenerators.put(
                BYTE_STRING,
                (field, containingBuilder) -> new ByteStringRandomizer(easyRandom.nextLong()).getRandomValue()
            );
        this.fieldGenerators.put(ENUM, (field, containingBuilder) -> getRandomEnumValue(field.getEnumType()));
        this.fieldGenerators.put(
                MESSAGE,
                (field, containingBuilder) ->
                    easyRandom.nextObject(
                        containingBuilder.newBuilderForField(field).getDefaultInstanceForType().getClass()
                    )
            );
    }

    private static Message.Builder instantiateMessageBuilder(Class<Message> clazz) {
        try {
            Method getDefaultInstanceMethod = clazz.getMethod("getDefaultInstance");
            Message message = (Message) getDefaultInstanceMethod.invoke(null);
            return message.newBuilderForType();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Message getRandomValue() {
        if (this.parameters.getExclusionPolicy().shouldBeExcluded(messageClass, context)) {
            return null;
        }
        Message.Builder builder = instantiateMessageBuilder(messageClass);
        Descriptor descriptor = builder.getDescriptorForType();
        List<FieldDescriptor> plainFields = descriptor
            .getFields()
            .stream()
            .filter(field -> field.getContainingOneof() == null)
            .toList();
        for (FieldDescriptor fieldDescriptor : plainFields) {
            populateField(fieldDescriptor, builder);
        }
        for (Descriptors.OneofDescriptor oneofDescriptor : descriptor.getOneofs()) {
            populateOneof(oneofDescriptor, builder);
        }
        return builder.build();
    }

    private void populateField(FieldDescriptor field, Message.Builder containingBuilder) {
        try {
            Field javaField = this.messageClass.getDeclaredField(field.getName() + "_");
            if (this.parameters.getExclusionPolicy().shouldBeExcluded(javaField, context, field)) {
                return;
            }
            if (this.parameters.getCustomRandomizerRegistry() instanceof ProtobufCustomRandomizerRegistry) {
                Randomizer<?> customRandomizer =
                    this.parameters.getCustomRandomizerRegistry().getRandomizer(javaField, field);
                if (customRandomizer != null) {
                    if (customRandomizer instanceof ContextAwareRandomizer) {
                        ((ContextAwareRandomizer<?>) customRandomizer).setRandomizerContext(context);
                    }
                    Object generated = customRandomizer.getRandomValue();
                    if (generated != null) {
                        containingBuilder.setField(field, generated);
                    }

                    return;
                }
            }
        } catch (NoSuchFieldException e) {
            //Bypassing
        }
        BiFunction<FieldDescriptor, Message.Builder, Object> fieldGenerator;
        if (field.isMapField()) {
            fieldGenerator =
                (fieldDescriptor, parentBuilder) -> {
                    Message.Builder mapEntryBuilder = parentBuilder.newBuilderForField(fieldDescriptor);
                    for (FieldDescriptor subField : fieldDescriptor.getMessageType().getFields()) {
                        populateField(subField, mapEntryBuilder);
                    }
                    return mapEntryBuilder.build();
                };
        } else {
            fieldGenerator = this.fieldGenerators.get(field.getJavaType());
        }

        if (field.isRepeated()) {
            IntegerRangeRandomizer collectionSizeRandomizer = new IntegerRangeRandomizer(
                parameters.getCollectionSizeRange().getMin(),
                parameters.getCollectionSizeRange().getMax(),
                easyRandom.nextLong()
            );
            for (int i = 0; i < collectionSizeRandomizer.getRandomValue(); i++) {
                Object generated = fieldGenerator.apply(field, containingBuilder);
                if (generated != null) containingBuilder.addRepeatedField(field, generated);
            }
        } else {
            Object generated = fieldGenerator.apply(field, containingBuilder);
            if (generated != null) containingBuilder.setField(field, generated);
        }
    }

    private void populateOneof(Descriptors.OneofDescriptor oneofDescriptor, Message.Builder builder) {
        int fieldCount = oneofDescriptor.getFieldCount();
        int oneofCase = easyRandom.nextInt(fieldCount);
        FieldDescriptor selectedCase = oneofDescriptor.getField(oneofCase);
        populateField(selectedCase, builder);
    }

    private EnumValueDescriptor getRandomEnumValue(EnumDescriptor enumDescriptor) {
        List<EnumValueDescriptor> values = enumDescriptor.getValues();
        int choice = easyRandom.nextInt(values.size());
        return values.get(choice);
    }

    @Override
    public void setRandomizerContext(RandomizerContext context) {
        this.context = context;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
