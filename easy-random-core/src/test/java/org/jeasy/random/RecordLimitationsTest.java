package org.jeasy.random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jeasy.random.FieldPredicates.ofType;

import java.util.Objects;
import org.jeasy.random.beans.DirectlyNested;
import org.jeasy.random.beans.NestedRecordThroughCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class RecordLimitationsTest {

    @Test
    void shouldGenerateNonPublicClasses() {
        //given
        EasyRandomParameters parameters = new EasyRandomParameters();
        EasyRandom easyRandom = new EasyRandom(parameters);

        //when
        WrapperOfNonPublic.NonPublicClass ordinaryClass = easyRandom.nextObject(
            WrapperOfNonPublic.NonPublicClass.class
        );

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
        assertThat(actual).isNotNull();
        assertThat(actual.value()).isNotNull();
        assertThat(actual.child().child().child())
            .as("On the 3rd level, the field values should equal null, i.e. end of nesting.")
            .isEqualTo(new DirectlyNested(null, null));
        assertThat(actual.child().child().value()).isNull();
    }

    @Test
    @Timeout(3)
    void shouldLimitTheNestingLevel_whenInRecursiveStructures() {
        //given
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.randomizationDepth(1);
        parameters.setCollectionSizeRange(new EasyRandomParameters.Range<>(1, 2));
        EasyRandom easyRandom = new EasyRandom(parameters);

        //when
        NestedRecordThroughCollection actual = easyRandom.nextObject(NestedRecordThroughCollection.class);

        //then
        assertThat(actual.children()).isNotEmpty();
        assertThat(actual.children().get(0))
            .as("On the 2nd level, the field should be initialized with empty list, i.e. end of nesting.")
            .isEqualTo(new NestedRecordThroughCollection(null));
    }

    @Test
    @Timeout(3)
    void depth_non_record() {
        //given
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.randomize(ofType(String.class), () -> "HELLO");
        parameters.randomizationDepth(3);
        parameters.setCollectionSizeRange(new EasyRandomParameters.Range<>(1, 2));
        EasyRandom easyRandom = new EasyRandom(parameters);

        //when
        TestRecord actualRecord = easyRandom.nextObject(TestRecord.class);
        TestClass actualClass = easyRandom.nextObject(TestClass.class);

        //then
        assertThat(actualRecord).isNotNull();
        assertThat(actualClass).isNotNull();
        assertThat(actualRecord.nested).isEqualTo(actualClass.nested);
    }

    public record TestRecord(Nested nested, String str) {}

    public static class TestClass {

        Nested nested;
        String str;
    }

    public static class Nested {

        String value;
        Nested nested;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Nested{");
            sb.append("value='").append(value).append('\'');
            sb.append(", nested=").append(nested);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Nested nested1 = (Nested) o;
            return Objects.equals(value, nested1.value) && Objects.equals(nested, nested1.nested);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, nested);
        }
    }

    public class WrapperOfNonPublic {

        record NonPublicRecord(String name) {}

        class NonPublicClass {

            String name;
        }
    }
}
