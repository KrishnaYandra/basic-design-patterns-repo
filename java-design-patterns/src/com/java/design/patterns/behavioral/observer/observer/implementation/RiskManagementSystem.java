package com.java.design.patterns.behavioral.observer.observer.implementation;

import com.java.design.patterns.behavioral.observer.event.MarketEvent;
import com.java.design.patterns.behavioral.observer.event.implementation.PriceChangeEvent;
import com.java.design.patterns.behavioral.observer.observer.MarketDataObserver;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RiskManagementSystem implements MarketDataObserver {
    private final Map<String, BigDecimal> riskLimits = new ConcurrentHashMap<>();
    private final Map<String, BigDecimal> currentExposure = new ConcurrentHashMap<>();
    
    public RiskManagementSystem() {
        // Set risk limits for different stocks
        riskLimits.put("AAPL", BigDecimal.valueOf(100000));
        riskLimits.put("GOOGL", BigDecimal.valueOf(150000));
        riskLimits.put("TSLA", BigDecimal.valueOf(75000));
    }
    
    @Override
    public void onMarketUpdate(MarketEvent event) {
        if (event instanceof PriceChangeEvent) {
            PriceChangeEvent priceEvent = (PriceChangeEvent) event;
            String symbol = event.getSymbol();
            
            // Calculate current exposure
            BigDecimal exposure = calculateExposure(symbol, priceEvent);
            currentExposure.put(symbol, exposure);
            
            // Check risk limits
            BigDecimal limit = riskLimits.get(symbol);
            if (limit != null && exposure.compareTo(limit) > 0) {
                triggerRiskAlert(symbol, exposure, limit);
            }
            
            // Monitor volatility
            if (priceEvent.getPercentageChange().abs().compareTo(BigDecimal.valueOf(5)) > 0) {
                triggerVolatilityAlert(symbol, priceEvent);
            }
        }
    }
    
    private BigDecimal calculateExposure(String symbol, PriceChangeEvent event) {
        // Simplified exposure calculation
        return event.getNewPrice().getPrice().multiply(BigDecimal.valueOf(event.getVolume()));
    }
    
    private void triggerRiskAlert(String symbol, BigDecimal exposure, BigDecimal limit) {
        System.out.println("⚠️ RISK ALERT: " + symbol + " exposure $" + exposure + 
                         " exceeds limit $" + limit);
    }
    
    private void triggerVolatilityAlert(String symbol, PriceChangeEvent event) {
        System.out.println("📊 VOLATILITY ALERT: " + symbol + " moved " + 
                         event.getPercentageChange() + "% - Current price: $" + 
                         event.getNewPrice().getPrice());
    }
    
    @Override
    public String getObserverName() {
        return "Risk Management System";
    }
}