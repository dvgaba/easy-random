package org.jeasy.random.beans;

public class WrapperOfNonPublic {

    record NonPublicRecord(String name) {}

    class NonPublicClass {

        String name;
    }
}
