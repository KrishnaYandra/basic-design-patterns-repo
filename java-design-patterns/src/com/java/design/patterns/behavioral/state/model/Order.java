package com.java.design.patterns.behavioral.state.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String customerId;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private List<OrderItem> items;
    private PaymentDetails paymentDetails;
    private ShippingDetails shippingDetails;
    private String cancellationReason;

    public Order(String orderId, String customerId, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
        this.items = new ArrayList<>();
    }

    // Getters and setters
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastModifiedAt() { return lastModifiedAt; }
    public void setLastModifiedAt(LocalDateTime time) { this.lastModifiedAt = time; }
    public List<OrderItem> getItems() { return items; }
    public void addItem(OrderItem item) { this.items.add(item); }
    public PaymentDetails getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(PaymentDetails details) { this.paymentDetails = details; }
    public ShippingDetails getShippingDetails() { return shippingDetails; }
    public void setShippingDetails(ShippingDetails details) { this.shippingDetails = details; }
    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String reason) { this.cancellationReason = reason; }
}
