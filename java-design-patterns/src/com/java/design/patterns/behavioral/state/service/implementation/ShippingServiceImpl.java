package com.java.design.patterns.behavioral.state.service.implementation;

import com.java.design.patterns.behavioral.state.model.Order;
import com.java.design.patterns.behavioral.state.service.ShippingService;

public class ShippingServiceImpl implements ShippingService {

    @Override
    public void scheduleShipment(Order order, String trackingNumber) {
        System.out.println("[ShippingService] Scheduling shipment for order: "
                + order.getOrderId() + " with tracking " + trackingNumber);
    }
}
