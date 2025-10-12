package com.java.design.patterns.structural.facade;

class MarketDataManager {
    void connectToBloomberg() { /* Complex connection logic */ }
    void connectToRefinitiv() { /* Different API calls */ }
    String getRealTimePrice(String symbol) { /* Aggregation logic */ return "100.25"; }
}