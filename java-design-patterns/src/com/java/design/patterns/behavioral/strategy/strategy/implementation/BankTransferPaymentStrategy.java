package com.java.design.patterns.behavioral.strategy.strategy.implementation;

import com.java.design.patterns.behavioral.strategy.exception.PaymentException;
import com.java.design.patterns.behavioral.strategy.model.PaymentRequest;
import com.java.design.patterns.behavioral.strategy.model.PaymentResponse;
import com.java.design.patterns.behavioral.strategy.strategy.PaymentStrategy;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.regex.Pattern;

public class BankTransferPaymentStrategy implements PaymentStrategy {
    
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[0-9]{10,16}$");
    private static final Pattern ROUTING_NUMBER_PATTERN = Pattern.compile("^[0-9]{9}$");
    
    @Override
    public PaymentResponse processPayment(PaymentRequest request) throws PaymentException {
        validatePaymentDetails(request);
        
        try {
            String accountNumber = request.getPaymentDetails().get("accountNumber");
            String routingNumber = request.getPaymentDetails().get("routingNumber");
            String bankName = request.getPaymentDetails().get("bankName");
            
            // Simulate ACH/Wire transfer processing
            boolean paymentSuccessful = simulateBankTransfer(accountNumber, routingNumber, 
                                                            request.getAmount());
            
            PaymentResponse response = new PaymentResponse();
            response.setTransactionId(generateTransactionId());
            response.setOrderId(request.getOrderId());
            response.setAmount(request.getAmount());
            response.setCurrency(request.getCurrency());
            response.setPaymentMethod("BANK_TRANSFER");
            
            if (paymentSuccessful) {
                response.setStatus(PaymentResponse.PaymentStatus.PENDING);
                response.setMessage("Bank transfer initiated. Processing may take 2-3 business days");
                response.setAuthorizationCode(generateReferenceNumber());
            } else {
                response.setStatus(PaymentResponse.PaymentStatus.FAILED);
                response.setMessage("Bank transfer initiation failed");
            }
            
            return response;
            
        } catch (Exception e) {
            throw new PaymentException("Bank transfer processing failed: " + e.getMessage(), 
                                      "BT_PROCESSING_ERROR", "BANK_TRANSFER");
        }
    }
    
    @Override
    public void validatePaymentDetails(PaymentRequest request) throws PaymentException {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("Invalid amount", "INVALID_AMOUNT");
        }
        
        String accountNumber = request.getPaymentDetails().get("accountNumber");
        if (accountNumber == null || !ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches()) {
            throw new PaymentException("Invalid account number format", "INVALID_ACCOUNT");
        }
        
        String routingNumber = request.getPaymentDetails().get("routingNumber");
        if (routingNumber == null || !ROUTING_NUMBER_PATTERN.matcher(routingNumber).matches()) {
            throw new PaymentException("Invalid routing number format", "INVALID_ROUTING");
        }
    }
    
    @Override
    public boolean isAvailable() {
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "BANK_TRANSFER";
    }
    
    private boolean simulateBankTransfer(String accountNumber, String routingNumber, 
                                        BigDecimal amount) {
        // Simulate 90% success rate
        return Math.random() > 0.10;
    }
    
    private String generateTransactionId() {
        return "BT-" + UUID.randomUUID().toString().substring(0, 18).toUpperCase();
    }
    
    private String generateReferenceNumber() {
        return "REF-" + UUID.randomUUID().toString().substring(0, 14).toUpperCase();
    }
}
