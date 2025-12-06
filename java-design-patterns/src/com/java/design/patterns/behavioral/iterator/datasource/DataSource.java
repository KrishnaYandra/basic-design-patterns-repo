package com.java.design.patterns.behavioral.iterator.datasource;

import com.java.design.patterns.behavioral.iterator.iterator.DataIterator;
import com.java.design.patterns.behavioral.iterator.predicates.FilterPredicate;

public interface DataSource {
    DataIterator createIterator();
    DataIterator createFilteredIterator(FilterPredicate predicate);
    int getTotalCount();
    String getSourceType();
}
