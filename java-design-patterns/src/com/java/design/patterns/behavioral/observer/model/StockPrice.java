package com.java.design.patterns.behavioral.observer.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockPrice {
    private final String symbol;
    private final BigDecimal price;
    private final LocalDateTime timestamp;
    
    public StockPrice(String symbol, BigDecimal price, LocalDateTime timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }
    
    // Getters
    public String getSymbol() { return symbol; }
    public BigDecimal getPrice() { return price; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return String.format("%s: $%.2f at %s", symbol, price, timestamp);
    }
}