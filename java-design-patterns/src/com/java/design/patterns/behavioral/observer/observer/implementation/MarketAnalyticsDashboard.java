package com.java.design.patterns.behavioral.observer.observer.implementation;

import com.java.design.patterns.behavioral.observer.event.MarketEvent;
import com.java.design.patterns.behavioral.observer.event.implementation.PriceChangeEvent;
import com.java.design.patterns.behavioral.observer.observer.MarketDataObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MarketAnalyticsDashboard implements MarketDataObserver {
    private final Map<String, List<MarketEvent>> eventHistory = new ConcurrentHashMap<>();
    
    @Override
    public void onMarketUpdate(MarketEvent event) {
        eventHistory.computeIfAbsent(event.getSymbol(), k -> new ArrayList<>()).add(event);
        
        if (event instanceof PriceChangeEvent) {
            updateDashboard((PriceChangeEvent) event);
        }
    }
    
    private void updateDashboard(PriceChangeEvent event) {
        System.out.println("📈 DASHBOARD UPDATE:");
        System.out.println("   Symbol: " + event.getSymbol());
        System.out.println("   Price: $" + event.getNewPrice().getPrice());
        System.out.println("   Change: " + event.getPriceChange() + " (" + 
                         event.getPercentageChange() + "%)");
        System.out.println("   Volume: " + event.getVolume());
        System.out.println("   Time: " + event.getTimestamp());
        System.out.println("─".repeat(50));
    }
    
    @Override
    public String getObserverName() {
        return "Market Analytics Dashboard";
    }
}