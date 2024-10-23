package org.jeasy.random;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SealedInterfaceTest {
    EasyRandomParameters parameters = new EasyRandomParameters();
    EasyRandom easyRandom = new EasyRandom(parameters);

    @Test
    void should_arrangeSealedInterface() {
        //when
        SealedInterfaceData actual = easyRandom.nextObject(SealedInterfaceData.class);

        //then
        assertThat(actual).isInstanceOfAny(ConcreteData1.class, ConcreteData2.class);
    }

    @Test
    void should_arrangeDataWithNestedSealedInterfaceField() {
        //when
        NestedSealedInterfaceData actual = easyRandom.nextObject(NestedSealedInterfaceData.class);

        //then
        assertThat(actual).isInstanceOf(ConcreteNested1.class);
        assertThat((ConcreteNested1) actual)
                .extracting(ConcreteNested1::sealedInterfaceData)
                .isInstanceOfAny(ConcreteData1.class, ConcreteData2.class);

    }

    @Test
    void should_arrangeRecordWithListWithSealedInterfaceField() {
        //when
        RootRecordWithNestedList actual = easyRandom.nextObject(RootRecordWithNestedList.class);

        //then
        assertThat(actual.nestedRecordWithSealedInterfaces().get(0))
                .extracting(NestedRecordWithSealedInterface::sealedInterfaceData)
                .isInstanceOfAny(ConcreteData1.class, ConcreteData2.class);
    }
}

record DataWithAbstract(Integer value,
                        SealedInterfaceData sealedInterfaceData,
                        ExcludedSealedInterface excludedSealedInterface,
                        NonSealedInterface nonSealedInterface) {
    DataWithAbstract {
    }

    DataWithAbstract(String name) {
        this(0, new ConcreteData1(""), new ExcludedData1(), new NonSealedInterface() {
        });
    }
}

sealed interface SealedInterfaceData permits ConcreteData1, ConcreteData2 {
}

record ConcreteData1(String value) implements SealedInterfaceData {
}

final class ConcreteData2 implements SealedInterfaceData {

    Integer value;

}

sealed interface ExcludedSealedInterface permits ExcludedData1 {
}

record ExcludedData1() implements ExcludedSealedInterface {
}

interface NonSealedInterface {
}

sealed interface NestedSealedInterfaceData permits ConcreteNested1 {
}

record ConcreteNested1(SealedInterfaceData sealedInterfaceData) implements NestedSealedInterfaceData {
}

record RootRecordWithNestedList(List<NestedRecordWithSealedInterface> nestedRecordWithSealedInterfaces) {
}

record NestedRecordWithSealedInterface(SealedInterfaceData sealedInterfaceData) {
}
