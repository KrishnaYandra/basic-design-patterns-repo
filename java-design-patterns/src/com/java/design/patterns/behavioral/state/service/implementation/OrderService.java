package com.java.design.patterns.behavioral.state.service.implementation;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.model.Order;
import com.java.design.patterns.behavioral.state.service.*;

import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private Map<String, OrderContext> orderContexts = new HashMap<>();
    private PaymentService paymentService;
    private InventoryService inventoryService;
    private ShippingService shippingService;
    private NotificationService notificationService;
    private AuditService auditService;

    public OrderService(PaymentService paymentService, InventoryService inventoryService,
                       ShippingService shippingService, NotificationService notificationService,
                       AuditService auditService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
        this.shippingService = shippingService;
        this.notificationService = notificationService;
        this.auditService = auditService;
    }

    public OrderContext createOrder(Order order) {
        OrderContext context = new OrderContext(order, paymentService, inventoryService,
                                               shippingService, notificationService, auditService);
        orderContexts.put(order.getOrderId(), context);
        System.out.println("Order created: " + order.getOrderId());
        return context;
    }

    public void processOrderPayment(String orderId)  {
        OrderContext context = orderContexts.get(orderId);
        if (context != null) {
            context.processPayment();
            context.confirmPayment();
        }
    }

    public void fulfillOrder(String orderId)  {
        OrderContext context = orderContexts.get(orderId);
        if (context != null) {
            context.prepareOrder();
        }
    }

    public OrderContext getOrderContext(String orderId) {
        return orderContexts.get(orderId);
    }
}
