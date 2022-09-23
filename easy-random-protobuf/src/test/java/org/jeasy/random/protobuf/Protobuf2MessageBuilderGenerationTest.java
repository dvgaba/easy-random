/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.random.protobuf;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.protobuf.StringValue;
import org.jeasy.random.protobuf.testing.proto2.EmbeddedProto2Message;
import org.jeasy.random.protobuf.testing.proto2.Proto2Message;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.protobuf.testing.proto2.Proto2Enum;
import org.jeasy.random.protobuf.testing.proto2.Proto2Message.Builder;
import org.junit.jupiter.api.Test;

class Protobuf2MessageBuilderGenerationTest {

    @Test
    void shouldGenerateTheSameValueForTheSameSeed() {
        EasyRandomParameters parameters = new EasyRandomParameters().seed(123L).collectionSizeRange(3, 10);
        EasyRandom easyRandom = new EasyRandom(parameters);

        Builder protoBuilderInstance = easyRandom.nextObject(Builder.class);

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

        assertThat(protoBuilderInstance.getEnumField()).isEqualTo(Proto2Enum.THIRD_VALUE);
        assertThat(protoBuilderInstance.getStringValueField())
            .isNotNull()
            .extracting(StringValue::getValue)
            .isEqualTo("tg");
        assertThat(protoBuilderInstance.getRepeatedStringFieldList())
            .containsExactly("AJVH", "WuGaTPB", "NuGSIFWDPVPqKClkqNpxLIRO", "jukCwoSTgRGMwWnAeflhVmclqMX", "bWyqZZW");

        assertThat(protoBuilderInstance.hasEmbeddedMessage()).isTrue();
        assertThat(protoBuilderInstance.getEmbeddedMessage())
            .satisfies(
                embeddedMessage -> {
                    assertThat(embeddedMessage.getStringField()).isEqualTo("LRHCsQ");
                    assertThat(embeddedMessage.getEnumField()).isEqualTo(Proto2Enum.THIRD_VALUE);
                }
            );
        assertThat(protoBuilderInstance.getOneofFieldCase().getNumber())
            .isNotEqualTo(Proto2Message.OneofFieldCase.ONEOFFIELD_NOT_SET);
        assertThat(protoBuilderInstance.getMapFieldMap())
            .hasSize(4)
            .containsEntry(
                "FcoiXCbvfaJXRzsMllsopqaJh",
                EmbeddedProto2Message
                    .newBuilder()
                    .setStringField("GisTfiLZvBZTpZtlTy")
                    .setEnumField(Proto2Enum.THIRD_VALUE)
                    .build()
            )
            .containsEntry(
                "YBTARDDDrSwFSHmNX",
                EmbeddedProto2Message
                    .newBuilder()
                    .setStringField("DLdnQAjfMAMqWBbmdqFVGBNAsVMBZX")
                    .setEnumField(Proto2Enum.FOURTH_VALUE)
                    .build()
            )
            .containsEntry(
                "mHKUfYwqKVzveNwvtrIpEXTGHGRQJNs",
                EmbeddedProto2Message
                    .newBuilder()
                    .setStringField("DaHxoNoJueuCTktIqEOZK")
                    .setEnumField(Proto2Enum.FOURTH_VALUE)
                    .build()
            )
            .containsEntry(
                "nNRZPqiaU",
                EmbeddedProto2Message
                    .newBuilder()
                    .setStringField("BQyytJgfXzAVTafnmwmsoZGvUFspz")
                    .setEnumField(Proto2Enum.THIRD_VALUE)
                    .build()
            );
    }
}
