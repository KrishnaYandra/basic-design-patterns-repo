package com.java.design.patterns.behavioral.observer.event.implementation;

import com.java.design.patterns.behavioral.observer.event.MarketEvent;
import com.java.design.patterns.behavioral.observer.model.*;

import java.math.BigDecimal;

public class PriceChangeEvent extends MarketEvent {
    private final StockPrice previousPrice;
    private final StockPrice newPrice;
    private final long volume;
    private final BigDecimal priceChange;
    private final BigDecimal percentageChange;
    
    public PriceChangeEvent(String symbol, StockPrice previousPrice, StockPrice newPrice, long volume) {
        super(symbol, EventType.PRICE_CHANGE);
        this.previousPrice = previousPrice;
        this.newPrice = newPrice;
        this.volume = volume;
        
        if (previousPrice != null) {
            this.priceChange = newPrice.getPrice().subtract(previousPrice.getPrice());
            this.percentageChange = priceChange.divide(previousPrice.getPrice(), 4, BigDecimal.ROUND_HALF_UP)
                                              .multiply(BigDecimal.valueOf(100));
        } else {
            this.priceChange = BigDecimal.ZERO;
            this.percentageChange = BigDecimal.ZERO;
        }
    }
    
    // Getters
    public StockPrice getPreviousPrice() { return previousPrice; }
    public StockPrice getNewPrice() { return newPrice; }
    public long getVolume() { return volume; }
    public BigDecimal getPriceChange() { return priceChange; }
    public BigDecimal getPercentageChange() { return percentageChange; }
}