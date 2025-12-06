package com.java.design.patterns.behavioral.state;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.model.*;
import com.java.design.patterns.behavioral.state.service.*;
import com.java.design.patterns.behavioral.state.service.implementation.*;

import java.math.BigDecimal;

public class OrderManagementDemo {
    public static void main(String[] args) {
        // Initialize services
        PaymentService paymentService = new PaymentServiceImpl();
        InventoryService inventoryService = new InventoryServiceImpl();
        ShippingService shippingService = new ShippingServiceImpl();
        NotificationService notificationService = new NotificationServiceImpl();
        AuditService auditService = new AuditServiceImpl();

        OrderService orderService = new OrderService(paymentService, inventoryService,
                                                     shippingService, notificationService,
                                                     auditService);

        // Create order
        Order order = new Order("ORD-12345", "CUST-001", new BigDecimal("299.99"));
        order.addItem(new OrderItem("PROD-101", "Laptop", 1, new BigDecimal("299.99")));
        
        OrderContext orderContext = orderService.createOrder(order);

        try {
            // Order workflow simulation
            System.out.println("\n=== Processing Order Workflow ===\n");
            
            orderService.processOrderPayment("ORD-12345");
            Thread.sleep(1000);
            
            orderService.fulfillOrder("ORD-12345");
            Thread.sleep(1000);
            
            orderContext.shipOrder("TRACK-XYZ789");
            Thread.sleep(1000);
            
            orderContext.deliverOrder();
            
            System.out.println("\n=== Order Successfully Completed ===");
            System.out.println("Final Status: " + orderContext.getStatus());
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
