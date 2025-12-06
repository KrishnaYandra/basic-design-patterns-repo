package com.java.design.patterns.behavioral.strategy.exception;

public class PaymentException extends Exception {
    private String errorCode;
    private String paymentMethod;
    
    public PaymentException(String message) {
        super(message);
    }
    
    public PaymentException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PaymentException(String message, String errorCode, String paymentMethod) {
        super(message);
        this.errorCode = errorCode;
        this.paymentMethod = paymentMethod;
    }
    
    public String getErrorCode() { return errorCode; }
    public String getPaymentMethod() { return paymentMethod; }
}
