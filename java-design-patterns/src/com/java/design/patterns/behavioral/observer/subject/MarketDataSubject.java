package com.java.design.patterns.behavioral.observer.subject;

import com.java.design.patterns.behavioral.observer.event.MarketEvent;
import com.java.design.patterns.behavioral.observer.observer.MarketDataObserver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface MarketDataSubject {
    void registerObserver(MarketDataObserver observer);
    void unregisterObserver(MarketDataObserver observer);
    void notifyObservers(MarketEvent event);
}