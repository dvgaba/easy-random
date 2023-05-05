package org.jeasy.random.beans;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecordLimitationsTest {

    @Test
    void shouldGenerateNonPublicClasses() {
        //given
        EasyRandomParameters parameters = new EasyRandomParameters();
        EasyRandom easyRandom = new EasyRandom(parameters);

        //when
        WrapperOfNonPublic.NonPublicClass ordinaryClass = easyRandom.nextObject(WrapperOfNonPublic.NonPublicClass.class);

        //then
        assertThat(ordinaryClass.name).isNotEmpty();
    }

    //This tst fail despite its counterpart regarding classes passes.
    @Test
    void shouldGenerateNonPublicRecords() {
        //given
        EasyRandomParameters parameters = new EasyRandomParameters();
        EasyRandom easyRandom = new EasyRandom(parameters);

        //when
        WrapperOfNonPublic.NonPublicRecord record = easyRandom.nextObject(WrapperOfNonPublic.NonPublicRecord.class);

        //then
        assertThat(record.name()).isNotEmpty();
    }

    @Test
    @Timeout(3)
    void shouldLimitTheNestingLevel_whenInDirectlyRecursiveStructures() {
        //given
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.randomizationDepth(2);
        EasyRandom easyRandom = new EasyRandom(parameters);

        //when
        DirectlyNested actual = easyRandom.nextObject(DirectlyNested.class);

        //then
        assertThat(actual.value()).isNotNull();
        assertThat(actual.child().child().child())
                .as("On the 3rd level, the field values should equal null, i.e. end of nesting.")
                .isEqualTo(new DirectlyNested(null,null));
        assertThat(actual.child().child().value()).isNotNull();
    }

    @Test
    @Timeout(3)
    void shouldLimitTheNestingLevel_whenInRecursiveStructures() {
        //given
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.randomizationDepth(1);
        parameters.setCollectionSizeRange(new EasyRandomParameters.Range<>(1,2));
        EasyRandom easyRandom = new EasyRandom(parameters);

        //when
        NestedRecordThroughCollection actual = easyRandom.nextObject(NestedRecordThroughCollection.class);

        //then
        assertThat(actual.children()).isNotEmpty();
        assertThat(actual.children().get(0).children().get(0))
                .as("On the 2nd level, the field should be initialized with empty list, i.e. end of nesting.")
                .isEqualTo(new NestedRecordThroughCollection(List.of()));
    }
}
