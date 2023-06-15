package org.jeasy.random.protobuf;

import org.jeasy.random.EasyRandomParameters;

public class ProtobufEasyRandomParameters extends EasyRandomParameters {

    public ProtobufEasyRandomParameters() {
        super.setCustomRandomizerRegistry(new ProtobufCustomRandomizerRegistry());
        super.setExclusionPolicy(new ProtobufExclusionPolicy());
    }
}
