package com.java.design.patterns.behavioral.state.model;

public enum OrderStatus {
    PENDING_PAYMENT("Pending Payment"),
    PAYMENT_PROCESSING("Payment Processing"),
    PAID("Paid"),
    PREPARING("Preparing"),
    SHIPPED("Shipped"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled"),
    REFUND_REQUESTED("Refund Requested"),
    REFUNDED("Refunded");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
