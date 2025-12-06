package com.java.design.patterns.behavioral.observer;

import com.java.design.patterns.behavioral.observer.observer.implementation.*;
import com.java.design.patterns.behavioral.observer.subject.implementation.*;

import java.math.BigDecimal;
import java.util.Random;

public class EnterpriseTraidingSystemDemo {
    public static void main(String[] args) {
        System.out.println("🚀 Enterprise Real-Time Trading Platform Starting...\n");
        
        // Create the market data feed (Subject)
        RealTimeMarketDataFeed marketFeed = new RealTimeMarketDataFeed();
        
        // Create sophisticated observers
        QuantitativeTradingStrategy momentumStrategy =
            new QuantitativeTradingStrategy("Momentum Strategy", BigDecimal.valueOf(3));
        QuantitativeTradingStrategy arbitrageStrategy = 
            new QuantitativeTradingStrategy("Arbitrage Strategy", BigDecimal.valueOf(1));
        RiskManagementSystem riskManager = new RiskManagementSystem();
        MarketAnalyticsDashboard dashboard = new MarketAnalyticsDashboard();
        
        // Register observers
        marketFeed.registerObserver(momentumStrategy);
        marketFeed.registerObserver(arbitrageStrategy);
        marketFeed.registerObserver(riskManager);
        marketFeed.registerObserver(dashboard);
        
        System.out.println("\n🎯 Market Trading Session Started\n");
        
        // Simulate real-time market data
        simulateMarketData(marketFeed);
        
        // Demonstrate dynamic observer management
        System.out.println("\n🔄 Unregistering Arbitrage Strategy...\n");
        marketFeed.unregisterObserver(arbitrageStrategy);
        
        // Continue simulation
        simulateMoreMarketData(marketFeed);
        
        System.out.println("\n✅ Trading Session Completed");
    }
    
    private static void simulateMarketData(RealTimeMarketDataFeed marketFeed) {
        String[] stocks = {"AAPL", "GOOGL", "TSLA", "MSFT", "AMZN"};
        BigDecimal[] basePrices = {
            BigDecimal.valueOf(150.00), BigDecimal.valueOf(2800.00), 
            BigDecimal.valueOf(250.00), BigDecimal.valueOf(300.00), 
            BigDecimal.valueOf(3200.00)
        };
        
        Random random = new Random();
        
        for (int i = 0; i < stocks.length; i++) {
            String stock = stocks[i];
            BigDecimal basePrice = basePrices[i];
            
            // Simulate price movements
            for (int j = 0; j < 3; j++) {
                double changePercent = (random.nextDouble() - 0.5) * 10; // -5% to +5%
                BigDecimal newPrice = basePrice.multiply(
                    BigDecimal.valueOf(1 + changePercent / 100));
                long volume = 1000 + random.nextInt(9000);
                
                marketFeed.updateStockPrice(stock, newPrice, volume);
                
                try {
                    Thread.sleep(500); // Simulate real-time delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    private static void simulateMoreMarketData(RealTimeMarketDataFeed marketFeed) {
        Random random = new Random();
        
        // Simulate high volatility event
        marketFeed.updateStockPrice("TSLA", BigDecimal.valueOf(275.50), 15000);
        
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        marketFeed.updateStockPrice("TSLA", BigDecimal.valueOf(290.25), 25000);
    }
}
