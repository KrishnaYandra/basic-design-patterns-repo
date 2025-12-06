package com.java.design.patterns.behavioral.strategy.model;

import java.math.BigDecimal;
import java.util.Map;

public class PaymentRequest {
    private String orderId;
    private BigDecimal amount;
    private String currency;
    private String customerId;
    private Map<String, String> paymentDetails;
    private Map<String, Object> metadata;
    
    // Constructors
    public PaymentRequest(String orderId, BigDecimal amount, String currency, 
                         String customerId, Map<String, String> paymentDetails) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.customerId = customerId;
        this.paymentDetails = paymentDetails;
    }
    
    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public Map<String, String> getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(Map<String, String> paymentDetails) { 
        this.paymentDetails = paymentDetails; 
    }
    
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
