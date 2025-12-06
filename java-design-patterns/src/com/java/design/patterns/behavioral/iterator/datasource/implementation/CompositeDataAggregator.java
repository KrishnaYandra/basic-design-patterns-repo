package com.java.design.patterns.behavioral.iterator.datasource.implementation;

import com.java.design.patterns.behavioral.iterator.datasource.DataSource;
import com.java.design.patterns.behavioral.iterator.iterator.DataIterator;
import com.java.design.patterns.behavioral.iterator.model.DataRecord;
import com.java.design.patterns.behavioral.iterator.predicates.FilterPredicate;

import java.util.*;

public class CompositeDataAggregator implements DataSource {
    private List<DataSource> dataSources;
    private String name;

    public CompositeDataAggregator(String name) {
        this.name = name;
        this.dataSources = new ArrayList<>();
    }

    public void addDataSource(DataSource source) {
        dataSources.add(source);
    }

    @Override
    public DataIterator createIterator() {
        return new CompositeIterator(dataSources, null);
    }

    @Override
    public DataIterator createFilteredIterator(FilterPredicate predicate) {
        return new CompositeIterator(dataSources, predicate);
    }

    @Override
    public int getTotalCount() {
        return dataSources.stream()
                         .mapToInt(DataSource::getTotalCount)
                         .sum();
    }

    @Override
    public String getSourceType() {
        return "COMPOSITE";
    }

    private class CompositeIterator implements DataIterator {
        private List<DataSource> sources;
        private FilterPredicate filter;
        private int currentSourceIndex;
        private DataIterator currentIterator;
        private int totalPosition;

        public CompositeIterator(List<DataSource> sources, FilterPredicate filter) {
            this.sources = sources;
            this.filter = filter;
            this.currentSourceIndex = 0;
            this.totalPosition = 0;
            
            if (!sources.isEmpty()) {
                currentIterator = filter == null 
                    ? sources.get(0).createIterator()
                    : sources.get(0).createFilteredIterator(filter);
            }
        }

        @Override
        public boolean hasNext() {
            if (currentIterator == null) {
                return false;
            }

            if (currentIterator.hasNext()) {
                return true;
            }

            // Move to next source
            while (currentSourceIndex < sources.size() - 1) {
                currentSourceIndex++;
                currentIterator = filter == null
                    ? sources.get(currentSourceIndex).createIterator()
                    : sources.get(currentSourceIndex).createFilteredIterator(filter);
                
                if (currentIterator.hasNext()) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public DataRecord next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements across sources");
            }
            
            DataRecord record = currentIterator.next();
            totalPosition++;
            
            // Add composite metadata
            record.addMetadata("aggregator", name);
            record.addMetadata("sourceIndex", currentSourceIndex);
            
            return record;
        }

        @Override
        public void reset() {
            currentSourceIndex = 0;
            totalPosition = 0;
            if (!sources.isEmpty()) {
                currentIterator = filter == null
                    ? sources.get(0).createIterator()
                    : sources.get(0).createFilteredIterator(filter);
            }
        }

        @Override
        public int getCurrentPosition() {
            return totalPosition;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public DataRecord previous() {
            throw new UnsupportedOperationException("Previous not supported for composite iterator");
        }
    }
}
