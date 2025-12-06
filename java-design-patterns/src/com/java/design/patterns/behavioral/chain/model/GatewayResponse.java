package com.java.design.patterns.behavioral.chain.model;

public class GatewayResponse {
    private boolean success;
    private String gatewayTransactionId;
    private String errorMessage;
    private String errorCode;
    
    public GatewayResponse(boolean success, String gatewayTransactionId, 
                          String errorMessage, String errorCode) {
        this.success = success;
        this.gatewayTransactionId = gatewayTransactionId;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public GatewayResponse(boolean success, String paymentExecutedSuccessfully) {
    }

    public boolean isSuccess() { return success; }
    public String getGatewayTransactionId() { return gatewayTransactionId; }
    public String getErrorMessage() { return errorMessage; }
    public String getErrorCode() { return errorCode; }
}