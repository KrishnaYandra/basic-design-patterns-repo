package com.java.design.patterns.behavioral.strategy.strategy.implementation;

import com.java.design.patterns.behavioral.strategy.exception.PaymentException;
import com.java.design.patterns.behavioral.strategy.model.PaymentRequest;
import com.java.design.patterns.behavioral.strategy.model.PaymentResponse;
import com.java.design.patterns.behavioral.strategy.strategy.PaymentStrategy;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.regex.Pattern;

public class PayPalPaymentStrategy implements PaymentStrategy {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final BigDecimal PAYPAL_FEE_PERCENTAGE = new BigDecimal("0.029");
    private static final BigDecimal PAYPAL_FIXED_FEE = new BigDecimal("0.30");
    
    @Override
    public PaymentResponse processPayment(PaymentRequest request) throws PaymentException {
        validatePaymentDetails(request);
        
        try {
            String email = request.getPaymentDetails().get("email");
            String paypalToken = request.getPaymentDetails().get("paypalToken");
            
            // Calculate total with PayPal fees
            BigDecimal totalAmount = calculateTotalWithFees(request.getAmount());
            
            // Simulate PayPal API call
            boolean paymentSuccessful = simulatePayPalAPI(email, paypalToken, totalAmount);
            
            PaymentResponse response = new PaymentResponse();
            response.setTransactionId(generateTransactionId());
            response.setOrderId(request.getOrderId());
            response.setAmount(totalAmount);
            response.setCurrency(request.getCurrency());
            response.setPaymentMethod("PAYPAL");
            
            if (paymentSuccessful) {
                response.setStatus(PaymentResponse.PaymentStatus.SUCCESS);
                response.setMessage("Payment processed successfully via PayPal");
                response.setAuthorizationCode(generatePayPalAuthCode());
            } else {
                response.setStatus(PaymentResponse.PaymentStatus.FAILED);
                response.setMessage("PayPal payment failed");
            }
            
            return response;
            
        } catch (Exception e) {
            throw new PaymentException("PayPal processing failed: " + e.getMessage(), 
                                      "PP_PROCESSING_ERROR", "PAYPAL");
        }
    }
    
    @Override
    public void validatePaymentDetails(PaymentRequest request) throws PaymentException {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("Invalid amount", "INVALID_AMOUNT");
        }
        
        String email = request.getPaymentDetails().get("email");
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new PaymentException("Invalid PayPal email format", "INVALID_EMAIL");
        }
        
        String paypalToken = request.getPaymentDetails().get("paypalToken");
        if (paypalToken == null || paypalToken.isEmpty()) {
            throw new PaymentException("PayPal token is required", "MISSING_TOKEN");
        }
    }
    
    @Override
    public boolean isAvailable() {
        // Check if PayPal API is available
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "PAYPAL";
    }
    
    private BigDecimal calculateTotalWithFees(BigDecimal amount) {
        BigDecimal percentageFee = amount.multiply(PAYPAL_FEE_PERCENTAGE);
        return amount.add(percentageFee).add(PAYPAL_FIXED_FEE);
    }
    
    private boolean simulatePayPalAPI(String email, String token, BigDecimal amount) {
        // Simulate 97% success rate
        return Math.random() > 0.03;
    }
    
    private String generateTransactionId() {
        return "PP-" + UUID.randomUUID().toString().substring(0, 18).toUpperCase();
    }
    
    private String generatePayPalAuthCode() {
        return "PPAUTH-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }
}
