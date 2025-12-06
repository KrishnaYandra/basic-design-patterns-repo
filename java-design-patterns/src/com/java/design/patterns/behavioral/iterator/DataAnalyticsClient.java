package com.java.design.patterns.behavioral.iterator;

import com.java.design.patterns.behavioral.iterator.datasource.implementation.CompositeDataAggregator;
import com.java.design.patterns.behavioral.iterator.datasource.implementation.DatabaseDataSource;
import com.java.design.patterns.behavioral.iterator.datasource.implementation.FileSystemDataSource;
import com.java.design.patterns.behavioral.iterator.iterator.DataIterator;
import com.java.design.patterns.behavioral.iterator.model.DataRecord;
import com.java.design.patterns.behavioral.iterator.predicates.FilterPredicate;

import java.sql.*;
import java.nio.file.*;

public class DataAnalyticsClient {
    
    public static void main(String[] args) throws SQLException {
        // Setup database connection (example)
        Connection dbConnection = DriverManager.getConnection(
            "jdbc:h2:mem:testdb", "sa", ""
        );
        
        // Create composite aggregator
        CompositeDataAggregator aggregator = new CompositeDataAggregator("EnterpriseDataPipeline");
        
        // Add multiple data sources
        aggregator.addDataSource(new DatabaseDataSource(dbConnection, "transactions", 100));
        aggregator.addDataSource(new FileSystemDataSource(Paths.get("/data/logs"), ".log"));
        aggregator.addDataSource(new DatabaseDataSource(dbConnection, "audit_logs", 50));
        
        System.out.println("Total records across all sources: " + aggregator.getTotalCount());
        
        // Example 1: Iterate through all records
        System.out.println("\n=== Processing All Records ===");
        DataIterator allRecords = aggregator.createIterator();
        int count = 0;
        while (allRecords.hasNext() && count < 10) {
            DataRecord record = allRecords.next();
            System.out.println(record);
            count++;
        }
        
        // Example 2: Filtered iteration - only recent records
        System.out.println("\n=== Processing Recent Records (Last 24 hours) ===");
        long oneDayAgo = System.currentTimeMillis() - (24 * 60 * 60 * 1000);
        FilterPredicate recentFilter = record -> record.getTimestamp() > oneDayAgo;
        
        DataIterator recentRecords = aggregator.createFilteredIterator(recentFilter);
        while (recentRecords.hasNext()) {
            DataRecord record = recentRecords.next();
            processRecord(record);
        }
        
        // Example 3: Multiple concurrent iterations with different filters
        System.out.println("\n=== Concurrent Analysis ===");
        
        FilterPredicate errorFilter = record -> 
            record.getContent().toLowerCase().contains("error");
        FilterPredicate warningFilter = record -> 
            record.getContent().toLowerCase().contains("warning");
        
        DataIterator errorIterator = aggregator.createFilteredIterator(errorFilter);
        DataIterator warningIterator = aggregator.createFilteredIterator(warningFilter);
        
        System.out.println("Errors found: " + countRecords(errorIterator));
        System.out.println("Warnings found: " + countRecords(warningIterator));
        
        dbConnection.close();
    }
    
    private static void processRecord(DataRecord record) {
        // Business logic: analytics, transformation, storage
        System.out.printf("Processing %s from %s at position %d%n",
            record.getId(), record.getSource(), 
            record.getMetadata().get("sourceIndex"));
    }
    
    private static int countRecords(DataIterator iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }
}
