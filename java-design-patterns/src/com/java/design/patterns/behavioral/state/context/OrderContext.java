package com.java.design.patterns.behavioral.state.context;

import com.java.design.patterns.behavioral.state.model.*;
import com.java.design.patterns.behavioral.state.service.*;
import com.java.design.patterns.behavioral.state.state.OrderState;
import com.java.design.patterns.behavioral.state.state.implementation.PendingPaymentState;

public class OrderContext {
    private Order order;
    private OrderState currentState;
    private OrderStatus status;
    
    // Service dependencies
    private PaymentService paymentService;
    private InventoryService inventoryService;
    private ShippingService shippingService;
    private NotificationService notificationService;
    private AuditService auditService;

    public OrderContext(Order order, PaymentService paymentService, 
                       InventoryService inventoryService, ShippingService shippingService,
                       NotificationService notificationService, AuditService auditService) {
        this.order = order;
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
        this.shippingService = shippingService;
        this.notificationService = notificationService;
        this.auditService = auditService;
        
        // Initialize with pending payment state
        setState(new PendingPaymentState());
        this.status = OrderStatus.PENDING_PAYMENT;
    }

    public void setState(OrderState state) {
        this.currentState = state;
        logStateTransition();
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        auditService.logStatusChange(order.getOrderId(), status);
    }

    private void logStateTransition() {
        System.out.println("Order " + order.getOrderId() + " transitioned to: " + 
                         currentState.getStateName());
        auditService.logStateTransition(order.getOrderId(), currentState.getStateName());
    }

    // Delegate operations to current state
    public void processPayment() {
        currentState.processPayment(this);
    }

    public void confirmPayment() {
        currentState.confirmPayment(this);
    }

    public void prepareOrder() {
        currentState.prepareOrder(this);
    }

    public void shipOrder(String trackingNumber) {
        currentState.shipOrder(this, trackingNumber);
    }

    public void deliverOrder() {
        currentState.deliverOrder(this);
    }

    public void cancelOrder(String reason) {
        currentState.cancelOrder(this, reason);
    }

    public void requestRefund(String reason) {
        currentState.requestRefund(this, reason);
    }

    public void processRefund() {
        currentState.processRefund(this);
    }

    // Getters for services and order
    public Order getOrder() { return order; }
    public OrderStatus getStatus() { return status; }
    public PaymentService getPaymentService() { return paymentService; }
    public InventoryService getInventoryService() { return inventoryService; }
    public ShippingService getShippingService() { return shippingService; }
    public NotificationService getNotificationService() { return notificationService; }
    public AuditService getAuditService() { return auditService; }
}
