package com.java.design.patterns.behavioral.state.service;

import com.java.design.patterns.behavioral.state.model.Order;
import com.java.design.patterns.behavioral.state.model.OrderItem;

import java.util.List;

public interface InventoryService {
    boolean reserveItems(List<OrderItem> items);
    void releaseItems(List<OrderItem> items);
    void initiateOrderPreparation(Order order);
}
