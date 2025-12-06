package com.java.design.patterns.behavioral.strategy.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {
    private String transactionId;
    private String orderId;
    private PaymentStatus status;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private LocalDateTime timestamp;
    private String message;
    private String authorizationCode;
    
    public enum PaymentStatus {
        SUCCESS, FAILED, PENDING, CANCELLED
    }
    
    // Constructors
    public PaymentResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    public PaymentResponse(String transactionId, String orderId, PaymentStatus status, 
                          BigDecimal amount, String paymentMethod) {
        this();
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }
    
    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getAuthorizationCode() { return authorizationCode; }
    public void setAuthorizationCode(String authorizationCode) { 
        this.authorizationCode = authorizationCode; 
    }
}
