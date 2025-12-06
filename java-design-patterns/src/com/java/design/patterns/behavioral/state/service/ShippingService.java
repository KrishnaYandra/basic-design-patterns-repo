package com.java.design.patterns.behavioral.state.service;

import com.java.design.patterns.behavioral.state.model.Order;

public interface ShippingService {
    void scheduleShipment(Order order, String trackingNumber);
}
