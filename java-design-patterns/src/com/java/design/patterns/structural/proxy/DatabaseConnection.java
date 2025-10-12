package com.java.design.patterns.structural.proxy;

import java.sql.ResultSet;

// Subject interface
interface DatabaseConnection {
    ResultSet executeQuery(String query);
    void executeUpdate(String query);
    void close();
}