package com.java.design.patterns.behavioral.observer.observer.implementation;

import com.java.design.patterns.behavioral.observer.event.MarketEvent;
import com.java.design.patterns.behavioral.observer.event.implementation.PriceChangeEvent;
import com.java.design.patterns.behavioral.observer.observer.MarketDataObserver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuantitativeTradingStrategy implements MarketDataObserver {
    private final String strategyName;
    private final Map<String, BigDecimal> positions = new ConcurrentHashMap<>();
    private final Map<String, List<BigDecimal>> priceHistory = new ConcurrentHashMap<>();
    private final BigDecimal riskThreshold;
    
    public QuantitativeTradingStrategy(String strategyName, BigDecimal riskThreshold) {
        this.strategyName = strategyName;
        this.riskThreshold = riskThreshold;
    }
    
    @Override
    public void onMarketUpdate(MarketEvent event) {
        if (event instanceof PriceChangeEvent) {
            PriceChangeEvent priceEvent = (PriceChangeEvent) event;
            String symbol = event.getSymbol();
            
            // Update price history for technical analysis
            priceHistory.computeIfAbsent(symbol, k -> new ArrayList<>())
                       .add(priceEvent.getNewPrice().getPrice());
            
            // Execute sophisticated trading logic
            if (shouldBuy(priceEvent)) {
                executeBuyOrder(symbol, priceEvent.getNewPrice().getPrice());
            } else if (shouldSell(priceEvent)) {
                executeSellOrder(symbol, priceEvent.getNewPrice().getPrice());
            }
            
            // Risk management
            if (isRiskThresholdExceeded(priceEvent)) {
                emergencyStopLoss(symbol);
            }
        }
    }
    
    private boolean shouldBuy(PriceChangeEvent event) {
        // Complex algorithmic decision based on:
        // - Moving averages
        // - RSI indicators
        // - Volume analysis
        // - Market sentiment
        
        List<BigDecimal> history = priceHistory.get(event.getSymbol());
        if (history == null || history.size() < 20) return false;
        
        // Simple moving average strategy (simplified for demo)
        BigDecimal sma20 = calculateSMA(history, 20);
        BigDecimal currentPrice = event.getNewPrice().getPrice();
        
        return currentPrice.compareTo(sma20.multiply(BigDecimal.valueOf(0.98))) < 0 && 
               event.getPercentageChange().compareTo(BigDecimal.valueOf(-2)) > 0;
    }
    
    private boolean shouldSell(PriceChangeEvent event) {
        BigDecimal currentPosition = positions.get(event.getSymbol());
        if (currentPosition == null || currentPosition.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        
        // Sell if profit exceeds 5% or loss exceeds 2%
        BigDecimal profitThreshold = currentPosition.multiply(BigDecimal.valueOf(1.05));
        BigDecimal lossThreshold = currentPosition.multiply(BigDecimal.valueOf(0.98));
        BigDecimal currentPrice = event.getNewPrice().getPrice();
        
        return currentPrice.compareTo(profitThreshold) > 0 || 
               currentPrice.compareTo(lossThreshold) < 0;
    }
    
    private void executeBuyOrder(String symbol, BigDecimal price) {
        positions.put(symbol, price);
        System.out.println("🟢 " + strategyName + " BOUGHT " + symbol + " at $" + price);
    }
    
    private void executeSellOrder(String symbol, BigDecimal price) {
        BigDecimal buyPrice = positions.get(symbol);
        if (buyPrice != null) {
            BigDecimal profit = price.subtract(buyPrice);
            positions.remove(symbol);
            System.out.println("🔴 " + strategyName + " SOLD " + symbol + " at $" + price + 
                             " (P&L: $" + profit + ")");
        }
    }
    
    private boolean isRiskThresholdExceeded(PriceChangeEvent event) {
        return event.getPercentageChange().abs().compareTo(riskThreshold) > 0;
    }
    
    private void emergencyStopLoss(String symbol) {
        positions.remove(symbol);
        System.out.println("🚨 " + strategyName + " EMERGENCY STOP LOSS for " + symbol);
    }
    
    private BigDecimal calculateSMA(List<BigDecimal> prices, int period) {
        if (prices.size() < period) return BigDecimal.ZERO;
        
        return prices.subList(prices.size() - period, prices.size())
                    .stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(period), 2, BigDecimal.ROUND_HALF_UP);
    }
    
    @Override
    public String getObserverName() {
        return strategyName;
    }
}