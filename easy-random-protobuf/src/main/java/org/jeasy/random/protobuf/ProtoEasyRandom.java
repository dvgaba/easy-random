package org.jeasy.random.protobuf;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class ProtoEasyRandom extends EasyRandom {

    public ProtoEasyRandom() {
        super(new ProtobufEasyRandomParameters());
    }

    public ProtoEasyRandom(EasyRandomParameters easyRandomParameters) {
        super(easyRandomParameters);
    }
}
