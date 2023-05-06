package org.jeasy.random.beans;

import java.util.List;

public record NestedRecordThroughCollection(List<NestedRecordThroughCollection> children) {}
