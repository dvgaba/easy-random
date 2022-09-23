package org.jeasy.random.protobuf;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.protobuf.Int32Value;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.protobuf.testing.proto2.Proto2Enum;
import org.jeasy.random.protobuf.testing.proto2.Proto2Message;
import org.jeasy.random.protobuf.testing.proto3.EmbeddedProto3Message;
import org.jeasy.random.protobuf.testing.proto3.Proto3Enum;
import org.jeasy.random.protobuf.testing.proto3.Proto3Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FieldExclusionTest {

    private EasyRandomParameters easyRandomParameters = new EasyRandomParameters();
    private EasyRandom easyRandom;

    @BeforeEach
    void setup() {
        easyRandomParameters = new EasyRandomParameters().collectionSizeRange(3, 3);
        easyRandomParameters.setExclusionPolicy(new ProtobufExclusionPolicy());
        easyRandom = new EasyRandom(easyRandomParameters);
    }

    @Nested
    class FieldExclusionProto3Test {

        @Test
        void shouldExcludeFieldByName() {
            easyRandomParameters = easyRandomParameters.excludeField(ProtobufPredicates.named("int32Field"));
            Proto3Message protoInstance = easyRandom.nextObject(Proto3Message.class);
            assertThat(protoInstance.getInt32Field()).isZero();
        }

        @Test
        void shouldExcludeFieldByType() {
            easyRandomParameters =
                easyRandomParameters.excludeField(ProtobufPredicates.ofProtobufType(Int32Value.class));
            Proto3Message protoInstance = easyRandom.nextObject(Proto3Message.class);
            assertThat(protoInstance.getInt32Field()).isZero();
        }

        @Test
        void shouldExcludeFieldOfTypeEnum() {
            easyRandomParameters =
                easyRandomParameters.excludeField(ProtobufPredicates.ofProtobufType(Proto3Enum.class));
            Proto3Message protoInstance = easyRandom.nextObject(Proto3Message.class);
            assertThat(protoInstance.getEnumField()).isEqualTo(Proto3Enum.UNKNOWN);
        }

        @Test
        void shouldExcludeFieldOfJavaTypeEnum() {
            easyRandomParameters =
                easyRandomParameters.excludeType(ProtobufPredicates.ofType(EmbeddedProto3Message.class));
            Proto3Message protoInstance = easyRandom.nextObject(Proto3Message.class);
            assertThat(protoInstance.getEmbeddedMessage().isInitialized()).isTrue();
            assertThat(protoInstance.getEmbeddedMessage().getStringField()).isNullOrEmpty();
            assertThat(protoInstance.getEmbeddedMessage().getEnumField()).isEqualTo(Proto3Enum.UNKNOWN);
        }
    }

    @Nested
    class FieldExclusionProto2Test {

        @Test
        void shouldExcludeFieldByName() {
            easyRandomParameters = easyRandomParameters.excludeField(ProtobufPredicates.named("int32Field"));
            Proto2Message protoInstance = easyRandom.nextObject(Proto2Message.class);
            assertThat(protoInstance.getInt32Field()).isZero();
        }

        @Test
        void shouldExcludeFieldOfTypeEnum() {
            easyRandomParameters =
                easyRandomParameters.excludeField(ProtobufPredicates.ofProtobufType(Proto2Enum.class));
            Proto2Message protoInstance = easyRandom.nextObject(Proto2Message.class);
            assertThat(protoInstance.getEnumField()).isEqualTo(Proto2Enum.THIRD_VALUE);
        }
    }
}
