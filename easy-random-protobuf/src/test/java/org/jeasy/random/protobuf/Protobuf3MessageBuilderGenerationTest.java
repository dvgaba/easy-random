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
import org.jeasy.random.protobuf.testing.proto3.Proto3Enum;
import org.jeasy.random.protobuf.testing.proto3.Proto3Message;
import org.junit.jupiter.api.Test;

class Protobuf3MessageBuilderGenerationTest {

    @Test
    void shouldGenerateTheSameValueForTheSameSeed() {
        EasyRandomParameters parameters = new ProtobufEasyRandomParameters().seed(123L).collectionSizeRange(3, 10);
        EasyRandom easyRandom = new EasyRandom(parameters);

        Proto3Message.Builder protoBuilderInstance = easyRandom.nextObject(Proto3Message.Builder.class);

        assertThat(protoBuilderInstance.getDoubleField()).isEqualTo(0.7231742029971469);
        assertThat(protoBuilderInstance.getFloatField()).isEqualTo(0.99089885f);
        assertThat(protoBuilderInstance.getInt32Field()).isEqualTo(1295249578);
        assertThat(protoBuilderInstance.getInt64Field()).isEqualTo(4672433029010564658L);
        assertThat(protoBuilderInstance.getUint32Field()).isEqualTo(-1680189627);
        assertThat(protoBuilderInstance.getUint64Field()).isEqualTo(4775521195821725379L);
        assertThat(protoBuilderInstance.getSint32Field()).isEqualTo(-1621910390);
        assertThat(protoBuilderInstance.getSint64Field()).isEqualTo(-2298228485105199876L);
        assertThat(protoBuilderInstance.getFixed32Field()).isEqualTo(-1219562352);
        assertThat(protoBuilderInstance.getFixed64Field()).isEqualTo(2992351518418085755L);
        assertThat(protoBuilderInstance.getSfixed32Field()).isEqualTo(-1366603797);
        assertThat(protoBuilderInstance.getSfixed64Field()).isEqualTo(-3758321679654915806L);
        assertThat(protoBuilderInstance.getBoolField()).isTrue();
        assertThat(protoBuilderInstance.getStringField()).isEqualTo("wSxRIexQAaxVLAiN");
        assertThat(protoBuilderInstance.getBytesField().toByteArray())
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

        assertThat(protoBuilderInstance.getEnumField()).isEqualTo(Proto3Enum.SECOND_VALUE);
        assertThat(protoBuilderInstance.getStringValueField())
            .isNotNull()
            .extracting(StringValue::getValue)
            .isEqualTo("tg");
        assertThat(protoBuilderInstance.getRepeatedStringFieldList())
            .containsExactly("AJVH", "WuGaTPB", "NuGSIFWDPVPqKClkqNpxLIRO", "jukCwoSTgRGMwWnAeflhVmclqMX", "bWyqZZW");

        assertThat(protoBuilderInstance.hasEmbeddedMessage()).isTrue();
        assertThat(protoBuilderInstance.getEmbeddedMessage())
            .satisfies(embeddedMessage -> {
                assertThat(embeddedMessage.getStringField()).isEqualTo("LRHCsQ");
                assertThat(embeddedMessage.getEnumField()).isEqualTo(Proto3Enum.UNKNOWN);
            });
        assertThat(protoBuilderInstance.getOneofFieldCase()).isEqualTo(Proto3Message.OneofFieldCase.FIRSTCHOICE);
        assertThat(protoBuilderInstance.getMapFieldMap())
            .hasSize(4)
            .containsEntry("Txwpix", Proto3Enum.FIRST_VALUE)
            .containsEntry("nNRZPqiaU", Proto3Enum.FIRST_VALUE)
            .containsEntry("vhuYKRd", Proto3Enum.FIRST_VALUE)
            .containsEntry("YBTARDDDrSwFSHmNX", Proto3Enum.FIRST_VALUE);
    }

    @Test
    void shouldSequentiallyGenerateDifferentObjects() {
        EasyRandomParameters parameters = new ProtobufEasyRandomParameters().seed(123L).collectionSizeRange(3, 10);
        EasyRandom easyRandom = new EasyRandom(parameters);

        Proto3Message.Builder firstInstance = easyRandom.nextObject(Proto3Message.Builder.class);
        Proto3Message.Builder secondInstance = easyRandom.nextObject(Proto3Message.Builder.class);

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
}
