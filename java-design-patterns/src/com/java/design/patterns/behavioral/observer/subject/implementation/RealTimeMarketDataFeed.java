package com.java.design.patterns.behavioral.observer.subject.implementation;

import com.java.design.patterns.behavioral.observer.event.*;
import com.java.design.patterns.behavioral.observer.event.implementation.*;
import com.java.design.patterns.behavioral.observer.model.*;
import com.java.design.patterns.behavioral.observer.observer.MarketDataObserver;
import com.java.design.patterns.behavioral.observer.subject.MarketDataSubject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class RealTimeMarketDataFeed implements MarketDataSubject {
    private final List<MarketDataObserver> observers = new CopyOnWriteArrayList<>();
    private final Map<String, StockPrice> currentPrices = new ConcurrentHashMap<>();
    private final Map<String, TradingVolume> volumes = new ConcurrentHashMap<>();
    
    @Override
    public void registerObserver(MarketDataObserver observer) {
        observers.add(observer);
        System.out.println("Observer registered: " + observer.getObserverName());
    }
    
    @Override
    public void unregisterObserver(MarketDataObserver observer) {
        observers.remove(observer);
        System.out.println("Observer unregistered: " + observer.getObserverName());
    }
    
    @Override
    public void notifyObservers(MarketEvent event) {
        System.out.println("🔄 Notifying " + observers.size() + " observers of " + event.getType());
        observers.parallelStream().forEach(observer -> {
            try {
                observer.onMarketUpdate(event);
            } catch (Exception e) {
                System.err.println("Error notifying observer " + observer.getObserverName() + ": " + e.getMessage());
            }
        });
    }
    
    public void updateStockPrice(String symbol, BigDecimal price, long volume) {
        StockPrice previousPrice = currentPrices.get(symbol);
        StockPrice newPrice = new StockPrice(symbol, price, LocalDateTime.now());
        currentPrices.put(symbol, newPrice);
        
        PriceChangeEvent event = new PriceChangeEvent(symbol, previousPrice, newPrice, volume);
        notifyObservers(event);
    }
    
    public void updateTradingVolume(String symbol, long volume) {
        TradingVolume newVolume = new TradingVolume(symbol, volume, LocalDateTime.now());
        volumes.put(symbol, newVolume);
        
        VolumeChangeEvent event = new VolumeChangeEvent(symbol, newVolume);
        notifyObservers(event);
    }
    
    public StockPrice getCurrentPrice(String symbol) {
        return currentPrices.get(symbol);
    }
}