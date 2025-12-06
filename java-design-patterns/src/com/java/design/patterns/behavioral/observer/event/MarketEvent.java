package com.java.design.patterns.behavioral.observer.event;

import com.java.design.patterns.behavioral.observer.model.EventType;

import java.time.LocalDateTime;

public abstract class MarketEvent {
    protected final String symbol;
    protected final LocalDateTime timestamp;
    protected final EventType type;
    
    public MarketEvent(String symbol, EventType type) {
        this.symbol = symbol;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getSymbol() { return symbol; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public EventType getType() { return type; }
}