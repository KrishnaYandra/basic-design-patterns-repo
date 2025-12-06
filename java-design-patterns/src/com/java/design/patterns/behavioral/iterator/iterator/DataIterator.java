package com.java.design.patterns.behavioral.iterator.iterator;

import com.java.design.patterns.behavioral.iterator.model.DataRecord;

public interface DataIterator {
    boolean hasNext();
    DataRecord next();
    void reset();
    int getCurrentPosition();
    boolean hasPrevious();
    DataRecord previous();
}
