package com.java.design.patterns.behavioral.observer.model;

import java.time.LocalDateTime;

public class TradingVolume {
    private final String symbol;
    private final long volume;
    private final LocalDateTime timestamp;
    
    public TradingVolume(String symbol, long volume, LocalDateTime timestamp) {
        this.symbol = symbol;
        this.volume = volume;
        this.timestamp = timestamp;
    }
    
    // Getters
    public String getSymbol() { return symbol; }
    public long getVolume() { return volume; }
    public LocalDateTime getTimestamp() { return timestamp; }
}