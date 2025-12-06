// PaymentResponse.java
package com.java.design.patterns.behavioral.chain.model;

import java.time.LocalDateTime;

public class PaymentResponse {
    private String transactionId;
    private boolean success;
    private String message;
    private ProcessingState finalState;
    private String gatewayTransactionId;
    private LocalDateTime processedAt;
    private PaymentRequest originalRequest;
    
    private PaymentResponse(String transactionId, boolean success, String message) {
        this.transactionId = transactionId;
        this.success = success;
        this.message = message;
        this.processedAt = LocalDateTime.now();
    }
    
    public static PaymentResponse success(String transactionId, String message) {
        return new PaymentResponse(transactionId, true, message);
    }
    
    public static PaymentResponse failure(String transactionId, String message) {
        return new PaymentResponse(transactionId, false, message);
    }
    
    // Getters and setters
    public String getTransactionId() { return transactionId; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public ProcessingState getFinalState() { return finalState; }
    public void setFinalState(ProcessingState finalState) { this.finalState = finalState; }
    public String getGatewayTransactionId() { return gatewayTransactionId; }
    public void setGatewayTransactionId(String gatewayTransactionId) { 
        this.gatewayTransactionId = gatewayTransactionId; 
    }
    public LocalDateTime getProcessedAt() { return processedAt; }
    public PaymentRequest getOriginalRequest() { return originalRequest; }
    public void setOriginalRequest(PaymentRequest originalRequest) { 
        this.originalRequest = originalRequest; 
    }
}
