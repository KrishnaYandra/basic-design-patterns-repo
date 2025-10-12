package com.java.design.patterns.structural.proxy;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

class DatabaseConnectionProxy implements DatabaseConnection {
    private RealDatabaseConnection realConnection;
    private String connectionString;
    private String userRole;
    private boolean isConnectionActive = false;
    private static Map<String, RealDatabaseConnection> connectionPool = new HashMap<>();
    
    public DatabaseConnectionProxy(String connectionString, String userRole) {
        this.connectionString = connectionString;
        this.userRole = userRole;
    }
    
    public ResultSet executeQuery(String query) {
        // Security check
        if (!hasQueryPermission(query)) {
            throw new SecurityException("Unauthorized query access for role: " + userRole);
        }
        
        // Lazy initialization with connection pooling
        if (realConnection == null) {
            realConnection = connectionPool.computeIfAbsent(connectionString, 
                k -> new RealDatabaseConnection(connectionString));
        }
        
        // Audit logging
        logAuditTrail(query);
        
        return realConnection.executeQuery(query);
    }

    @Override
    public void executeUpdate(String query) {

    }

    @Override
    public void close() {

    }

    private boolean hasQueryPermission(String query) {
        return !query.toLowerCase().contains("drop") || "ADMIN".equals(userRole);
    }
    
    private void logAuditTrail(String query) {
        System.out.println("AUDIT: User " + userRole + " executed: " + query);
    }
}