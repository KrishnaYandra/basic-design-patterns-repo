package com.java.design.patterns.behavioral.state.service.implementation;

import com.java.design.patterns.behavioral.state.model.Order;
import com.java.design.patterns.behavioral.state.model.OrderItem;
import com.java.design.patterns.behavioral.state.service.InventoryService;

import java.util.List;

public class InventoryServiceImpl implements InventoryService {

    @Override
    public boolean reserveItems(List<OrderItem> items) {
        System.out.println("[InventoryService] Reserving items:");
        for (OrderItem item : items) {
            System.out.println("  - " + item.getProductId() + " x " + item.getQuantity());
        }
        // Dummy logic – always reserves successfully
        return true;
    }

    @Override
    public void releaseItems(List<OrderItem> items) {
        System.out.println("[InventoryService] Releasing reserved items:");
        for (OrderItem item : items) {
            System.out.println("  - " + item.getProductId() + " x " + item.getQuantity());
        }
    }

    @Override
    public void initiateOrderPreparation(Order order) {
        System.out.println("[InventoryService] Preparing order in warehouse: " + order.getOrderId());
    }
}
