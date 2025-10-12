package com.java.design.patterns.structural.facade;

class OrderManagementSystem {
    void routeToNYSE(String order) { /* Exchange-specific routing */ }
    void routeToNASDAQ(String order) { /* Different protocols */ }
    String checkOrderStatus(String orderId) { /* Status tracking */ return "Filled"; }
}