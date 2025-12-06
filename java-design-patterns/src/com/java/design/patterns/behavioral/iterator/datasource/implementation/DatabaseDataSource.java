package com.java.design.patterns.behavioral.iterator.datasource.implementation;

import com.java.design.patterns.behavioral.iterator.datasource.DataSource;
import com.java.design.patterns.behavioral.iterator.iterator.DataIterator;
import com.java.design.patterns.behavioral.iterator.model.DataRecord;
import com.java.design.patterns.behavioral.iterator.predicates.FilterPredicate;

import java.sql.*;
import java.util.*;

public class DatabaseDataSource implements DataSource {
    private Connection connection;
    private String tableName;
    private int batchSize;

    public DatabaseDataSource(Connection connection, String tableName, int batchSize) {
        this.connection = connection;
        this.tableName = tableName;
        this.batchSize = batchSize;
    }

    @Override
    public DataIterator createIterator() {
        return new DatabaseIterator(connection, tableName, batchSize, null);
    }

    @Override
    public DataIterator createFilteredIterator(FilterPredicate predicate) {
        return new DatabaseIterator(connection, tableName, batchSize, predicate);
    }

    @Override
    public int getTotalCount() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getSourceType() {
        return "DATABASE";
    }

    private class DatabaseIterator implements DataIterator {
        private Connection conn;
        private String table;
        private int batchSize;
        private FilterPredicate filter;
        private List<DataRecord> currentBatch;
        private int batchOffset;
        private int indexInBatch;
        private int totalProcessed;
        private boolean hasMoreBatches;

        public DatabaseIterator(Connection conn, String table, int batchSize, FilterPredicate filter) {
            this.conn = conn;
            this.table = table;
            this.batchSize = batchSize;
            this.filter = filter;
            this.batchOffset = 0;
            this.indexInBatch = 0;
            this.totalProcessed = 0;
            this.currentBatch = new ArrayList<>();
            this.hasMoreBatches = true;
            loadNextBatch();
        }

        private void loadNextBatch() {
            currentBatch.clear();
            indexInBatch = 0;
            
            String query = String.format(
                "SELECT id, content, timestamp FROM %s LIMIT %d OFFSET %d",
                table, batchSize, batchOffset
            );

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                
                while (rs.next()) {
                    DataRecord record = new DataRecord(
                        rs.getString("id"),
                        "DATABASE",
                        rs.getString("content"),
                        rs.getLong("timestamp")
                    );
                    
                    if (filter == null || filter.test(record)) {
                        currentBatch.add(record);
                    }
                }
                
                hasMoreBatches = currentBatch.size() == batchSize;
                batchOffset += batchSize;
                
            } catch (SQLException e) {
                e.printStackTrace();
                hasMoreBatches = false;
            }
        }

        @Override
        public boolean hasNext() {
            if (indexInBatch < currentBatch.size()) {
                return true;
            }
            
            if (hasMoreBatches) {
                loadNextBatch();
                return indexInBatch < currentBatch.size();
            }
            
            return false;
        }

        @Override
        public DataRecord next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            
            DataRecord record = currentBatch.get(indexInBatch++);
            totalProcessed++;
            return record;
        }

        @Override
        public void reset() {
            batchOffset = 0;
            indexInBatch = 0;
            totalProcessed = 0;
            hasMoreBatches = true;
            loadNextBatch();
        }

        @Override
        public int getCurrentPosition() {
            return totalProcessed;
        }

        @Override
        public boolean hasPrevious() {
            return false; // Not implemented for database iterator
        }

        @Override
        public DataRecord previous() {
            throw new UnsupportedOperationException("Previous not supported for database iterator");
        }
    }
}
