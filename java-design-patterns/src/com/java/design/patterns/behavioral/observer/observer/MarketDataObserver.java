package com.java.design.patterns.behavioral.observer.observer;

import com.java.design.patterns.behavioral.observer.event.MarketEvent;

public interface MarketDataObserver {
    void onMarketUpdate(MarketEvent event);
    String getObserverName();
}