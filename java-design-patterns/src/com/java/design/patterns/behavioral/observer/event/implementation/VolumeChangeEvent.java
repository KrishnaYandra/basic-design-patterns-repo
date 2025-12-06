package com.java.design.patterns.behavioral.observer.event.implementation;

import com.java.design.patterns.behavioral.observer.event.MarketEvent;
import com.java.design.patterns.behavioral.observer.model.EventType;
import com.java.design.patterns.behavioral.observer.model.TradingVolume;

public class VolumeChangeEvent extends MarketEvent {
    private final TradingVolume volume;
    
    public VolumeChangeEvent(String symbol, TradingVolume volume) {
        super(symbol, EventType.VOLUME_CHANGE);
        this.volume = volume;
    }
    
    public TradingVolume getVolume() { return volume; }
}