package com.java.design.patterns.behavioral.iterator.predicates;

import com.java.design.patterns.behavioral.iterator.model.DataRecord;

@FunctionalInterface
public interface FilterPredicate {
    boolean test(DataRecord record);
}
