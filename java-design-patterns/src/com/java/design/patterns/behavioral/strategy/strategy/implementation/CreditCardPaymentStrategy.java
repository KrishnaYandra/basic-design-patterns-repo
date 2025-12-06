package com.java.design.patterns.behavioral.strategy.strategy.implementation;

import com.java.design.patterns.behavioral.strategy.exception.PaymentException;
import com.java.design.patterns.behavioral.strategy.model.PaymentRequest;
import com.java.design.patterns.behavioral.strategy.model.PaymentResponse;
import com.java.design.patterns.behavioral.strategy.strategy.PaymentStrategy;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.regex.Pattern;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^[0-9]{13,19}$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^[0-9]{3,4}$");
    private static final BigDecimal MAX_TRANSACTION_AMOUNT = new BigDecimal("50000.00");
    
    @Override
    public PaymentResponse processPayment(PaymentRequest request) throws PaymentException {
        validatePaymentDetails(request);
        
        try {
            // Simulate credit card processing
            String cardNumber = request.getPaymentDetails().get("cardNumber");
            String cvv = request.getPaymentDetails().get("cvv");
            String expiryDate = request.getPaymentDetails().get("expiryDate");
            
            // Simulate payment gateway call
            boolean paymentSuccessful = simulateCreditCardGateway(cardNumber, cvv, 
                                                                  expiryDate, request.getAmount());
            
            PaymentResponse response = new PaymentResponse();
            response.setTransactionId(generateTransactionId());
            response.setOrderId(request.getOrderId());
            response.setAmount(request.getAmount());
            response.setCurrency(request.getCurrency());
            response.setPaymentMethod("CREDIT_CARD");
            
            if (paymentSuccessful) {
                response.setStatus(PaymentResponse.PaymentStatus.SUCCESS);
                response.setMessage("Payment processed successfully via Credit Card");
                response.setAuthorizationCode(generateAuthCode());
            } else {
                response.setStatus(PaymentResponse.PaymentStatus.FAILED);
                response.setMessage("Credit card payment declined");
            }
            
            return response;
            
        } catch (Exception e) {
            throw new PaymentException("Credit card processing failed: " + e.getMessage(), 
                                      "CC_PROCESSING_ERROR", "CREDIT_CARD");
        }
    }
    
    @Override
    public void validatePaymentDetails(PaymentRequest request) throws PaymentException {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("Invalid amount", "INVALID_AMOUNT");
        }
        
        if (request.getAmount().compareTo(MAX_TRANSACTION_AMOUNT) > 0) {
            throw new PaymentException("Amount exceeds maximum transaction limit", 
                                      "AMOUNT_EXCEEDS_LIMIT");
        }
        
        String cardNumber = request.getPaymentDetails().get("cardNumber");
        if (cardNumber == null || !CARD_NUMBER_PATTERN.matcher(cardNumber.replaceAll("\\s", "")).matches()) {
            throw new PaymentException("Invalid card number format", "INVALID_CARD_NUMBER");
        }
        
        String cvv = request.getPaymentDetails().get("cvv");
        if (cvv == null || !CVV_PATTERN.matcher(cvv).matches()) {
            throw new PaymentException("Invalid CVV format", "INVALID_CVV");
        }
        
        String expiryDate = request.getPaymentDetails().get("expiryDate");
        if (expiryDate == null || !isValidExpiryDate(expiryDate)) {
            throw new PaymentException("Invalid or expired card", "CARD_EXPIRED");
        }
    }
    
    @Override
    public boolean isAvailable() {
        // Check if credit card gateway is available
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "CREDIT_CARD";
    }
    
    private boolean simulateCreditCardGateway(String cardNumber, String cvv, 
                                             String expiryDate, BigDecimal amount) {
        // Simulate 95% success rate
        return Math.random() > 0.05;
    }
    
    private String generateTransactionId() {
        return "CC-" + UUID.randomUUID().toString().substring(0, 18).toUpperCase();
    }
    
    private String generateAuthCode() {
        return "AUTH-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }
    
    private boolean isValidExpiryDate(String expiryDate) {
        // Simple validation - format MM/YY
        try {
            String[] parts = expiryDate.split("/");
            if (parts.length != 2) return false;
            
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt("20" + parts[1]);
            
            if (month < 1 || month > 12) return false;
            
            // Simple year check
            return year >= 2025;
        } catch (Exception e) {
            return false;
        }
    }
}
