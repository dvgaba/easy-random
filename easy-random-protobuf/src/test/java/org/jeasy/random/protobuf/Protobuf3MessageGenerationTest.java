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

import static org.assertj.core.api.Assertions.assertThat;

import com.google.protobuf.StringValue;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.protobuf.testing.proto3.EmbeddedProto3Message;
import org.jeasy.random.protobuf.testing.proto3.Proto3Enum;
import org.jeasy.random.protobuf.testing.proto3.Proto3Message;
import org.junit.jupiter.api.Test;

class Protobuf3MessageGenerationTest {

    @Test
    void generatedValuesShouldBeValidAccordingToValidationConstraints() {
        EasyRandom easyRandom = new EasyRandom();
        Proto3Message protoInstance = easyRandom.nextObject(Proto3Message.class);

        assertThat(protoInstance.getDoubleField()).isNotZero();
        assertThat(protoInstance.getFloatField()).isNotZero();
        assertThat(protoInstance.getInt32Field()).isNotZero();
        assertThat(protoInstance.getInt64Field()).isNotZero();
        assertThat(protoInstance.getUint32Field()).isNotZero();
        assertThat(protoInstance.getUint64Field()).isNotZero();
        assertThat(protoInstance.getSint32Field()).isNotZero();
        assertThat(protoInstance.getSint64Field()).isNotZero();
        assertThat(protoInstance.getFixed32Field()).isNotZero();
        assertThat(protoInstance.getFixed64Field()).isNotZero();
        assertThat(protoInstance.getSfixed32Field()).isNotZero();
        assertThat(protoInstance.getSfixed64Field()).isNotZero();
        assertThat(protoInstance.getBoolField()).isTrue();
        assertThat(protoInstance.getStringField()).isNotBlank();
        assertThat(protoInstance.getBytesField()).isNotEmpty();
        assertThat(protoInstance.getEnumField()).isIn((Object[]) Proto3Enum.values());
        assertThat(protoInstance.getStringValueField()).isNotNull();
        assertThat(protoInstance.getRepeatedStringFieldList()).isNotEmpty();

        assertThat(protoInstance.hasEmbeddedMessage()).isTrue();
        assertThat(protoInstance.getEmbeddedMessage())
            .satisfies(embeddedMessage -> {
                assertThat(embeddedMessage.getStringField()).isNotBlank();
                assertThat(embeddedMessage.getEnumField()).isIn((Object[]) Proto3Enum.values());
            });
        assertThat(protoInstance.getOneofFieldCase()).isNotEqualTo(Proto3Message.OneofFieldCase.ONEOFFIELD_NOT_SET);
        assertThat(protoInstance.getMapFieldMap()).isNotEmpty();
    }

    @Test
    void shouldUseCollectionSizeRangeParameters() {
        EasyRandomParameters parameters = new EasyRandomParameters().collectionSizeRange(3, 3);
        EasyRandom easyRandom = new EasyRandom(parameters);

        Proto3Message protoInstance = easyRandom.nextObject(Proto3Message.class);

        assertThat(protoInstance.getRepeatedStringFieldList()).hasSize(3);
        assertThat(protoInstance.getMapFieldMap()).hasSize(3);
    }

    @Test
    void shouldGenerateTheSameValueForTheSameSeed() {
        EasyRandomParameters parameters = new EasyRandomParameters().seed(123L).collectionSizeRange(3, 10);
        EasyRandom easyRandom = new EasyRandom(parameters);

        Proto3Message protoInstance = easyRandom.nextObject(Proto3Message.class);

        assertThat(protoInstance.getDoubleField()).isEqualTo(0.7231742029971469);
        assertThat(protoInstance.getFloatField()).isEqualTo(0.99089885f);
        assertThat(protoInstance.getInt32Field()).isEqualTo(1295249578);
        assertThat(protoInstance.getInt64Field()).isEqualTo(4672433029010564658L);
        assertThat(protoInstance.getUint32Field()).isEqualTo(-1680189627);
        assertThat(protoInstance.getUint64Field()).isEqualTo(4775521195821725379L);
        assertThat(protoInstance.getSint32Field()).isEqualTo(-1621910390);
        assertThat(protoInstance.getSint64Field()).isEqualTo(-2298228485105199876L);
        assertThat(protoInstance.getFixed32Field()).isEqualTo(-1219562352);
        assertThat(protoInstance.getFixed64Field()).isEqualTo(2992351518418085755L);
        assertThat(protoInstance.getSfixed32Field()).isEqualTo(-1366603797);
        assertThat(protoInstance.getSfixed64Field()).isEqualTo(-3758321679654915806L);
        assertThat(protoInstance.getBoolField()).isTrue();
        assertThat(protoInstance.getStringField()).isEqualTo("wSxRIexQAaxVLAiN");
        assertThat(protoInstance.getBytesField().toByteArray())
            .containsExactly(
                53,
                114,
                79,
                60,
                -14,
                -35,
                50,
                97,
                116,
                107,
                41,
                53,
                -39,
                -28,
                114,
                79,
                -111,
                98,
                -14,
                -11,
                -97,
                102,
                -22,
                83,
                -126,
                104,
                -108,
                -59,
                -97,
                93,
                -122,
                -67
            );

        assertThat(protoInstance.getEnumField()).isEqualTo(Proto3Enum.SECOND_VALUE);
        assertThat(protoInstance.getStringValueField()).isNotNull().extracting(StringValue::getValue).isEqualTo("tg");
        assertThat(protoInstance.getRepeatedStringFieldList())
            .containsExactly("AJVH", "WuGaTPB", "NuGSIFWDPVPqKClkqNpxLIRO", "jukCwoSTgRGMwWnAeflhVmclqMX", "bWyqZZW");

        assertThat(protoInstance.hasEmbeddedMessage()).isTrue();
        assertThat(protoInstance.getEmbeddedMessage())
            .satisfies(embeddedMessage -> {
                assertThat(embeddedMessage.getStringField()).isEqualTo("LRHCsQ");
                assertThat(embeddedMessage.getEnumField()).isEqualTo(Proto3Enum.UNKNOWN);
            });
        assertThat(protoInstance.getOneofFieldCase()).isEqualTo(Proto3Message.OneofFieldCase.FIRSTCHOICE);
        assertThat(protoInstance.getMapFieldMap())
            .hasSize(4)
            .containsEntry("Txwpix", Proto3Enum.FIRST_VALUE)
            .containsEntry("nNRZPqiaU", Proto3Enum.FIRST_VALUE)
            .containsEntry("vhuYKRd", Proto3Enum.FIRST_VALUE)
            .containsEntry("YBTARDDDrSwFSHmNX", Proto3Enum.FIRST_VALUE);
    }

    @Test
    void shouldSequentiallyGenerateDifferentObjects() {
        EasyRandomParameters parameters = new EasyRandomParameters().seed(123L).collectionSizeRange(3, 10);
        EasyRandom easyRandom = new EasyRandom(parameters);

        Proto3Message firstInstance = easyRandom.nextObject(Proto3Message.class);
        Proto3Message secondInstance = easyRandom.nextObject(Proto3Message.class);

        assertThat(firstInstance.getDoubleField()).isNotEqualTo(secondInstance.getDoubleField());
        assertThat(firstInstance.getFloatField()).isNotEqualTo(secondInstance.getFloatField());
        assertThat(firstInstance.getInt32Field()).isNotEqualTo(secondInstance.getInt32Field());
        assertThat(firstInstance.getInt64Field()).isNotEqualTo(secondInstance.getInt64Field());
        assertThat(firstInstance.getUint32Field()).isNotEqualTo(secondInstance.getUint32Field());
        assertThat(firstInstance.getUint64Field()).isNotEqualTo(secondInstance.getUint64Field());
        assertThat(firstInstance.getSint32Field()).isNotEqualTo(secondInstance.getSint32Field());
        assertThat(firstInstance.getSint64Field()).isNotEqualTo(secondInstance.getSint64Field());
        assertThat(firstInstance.getFixed32Field()).isNotEqualTo(secondInstance.getFixed32Field());
        assertThat(firstInstance.getFixed64Field()).isNotEqualTo(secondInstance.getFixed64Field());
        assertThat(firstInstance.getSfixed32Field()).isNotEqualTo(secondInstance.getSfixed32Field());
        assertThat(firstInstance.getSfixed64Field()).isNotEqualTo(secondInstance.getSfixed64Field());

        assertThat(firstInstance.getStringField()).isNotEqualTo(secondInstance.getStringField());
        assertThat(firstInstance.getBytesField()).isNotEqualTo(secondInstance.getBytesField());

        assertThat(firstInstance.getStringValueField()).isNotEqualTo(secondInstance.getStringValueField());
        assertThat(firstInstance.getRepeatedStringFieldList())
            .isNotEqualTo(secondInstance.getRepeatedStringFieldList());

        assertThat(firstInstance.hasEmbeddedMessage()).isTrue();
        assertThat(firstInstance.getEmbeddedMessage())
            .satisfies(embeddedMessage -> {
                assertThat(embeddedMessage.getStringField())
                    .isNotEqualTo(secondInstance.getEmbeddedMessage().getStringField());
            });
        assertThat(firstInstance.getMapFieldMap()).isNotEqualTo(secondInstance.getMapFieldMap());
    }

    @Test
    void shouldUseCustomRandomizerForSubMessageWhenItsDefined() {
        EmbeddedProto3Message customEmbeddedMessage = EmbeddedProto3Message
            .newBuilder()
            .setStringField("custom string value")
            .setEnumField(Proto3Enum.FIRST_VALUE)
            .build();
        EasyRandomParameters parameters = new EasyRandomParameters()
            .randomize(EmbeddedProto3Message.class, () -> customEmbeddedMessage);
        EasyRandom easyRandom = new EasyRandom(parameters);

        Proto3Message protoInstance = easyRandom.nextObject(Proto3Message.class);

        assertThat(protoInstance.hasEmbeddedMessage()).isTrue();
        assertThat(protoInstance.getEmbeddedMessage()).isEqualTo(customEmbeddedMessage);
    }
}
