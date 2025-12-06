// 1. ValidationHandler.java - Validates request integrity
package com.java.design.patterns.behavioral.chain.handler;

import com.java.design.patterns.behavioral.chain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ValidationHandler extends PaymentHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ValidationHandler.class);

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final BigDecimal MIN_AMOUNT = new BigDecimal("0.50");
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("999999.99");
    
    public ValidationHandler() {
        super("ValidationHandler");
    }
    
    @Override
    protected PaymentResponse process(PaymentRequest request) {
        logger.info("Validating payment request fields...");
        
        // Validate amount
        if (request.getAmount() == null) {
            request.addError(new ValidationError("amount", "Amount is required", ErrorSeverity.ERROR));
        } else if (request.getAmount().compareTo(MIN_AMOUNT) < 0) {
            request.addError(new ValidationError("amount", 
                "Amount must be at least " + MIN_AMOUNT, ErrorSeverity.ERROR));
        } else if (request.getAmount().compareTo(MAX_AMOUNT) > 0) {
            request.addError(new ValidationError("amount", 
                "Amount exceeds maximum " + MAX_AMOUNT, ErrorSeverity.ERROR));
        }
        
        // Validate currency
        if (request.getCurrency() == null || request.getCurrency().length() != 3) {
            request.addError(new ValidationError("currency", 
                "Invalid currency code", ErrorSeverity.ERROR));
        }
        
        // Validate customer details
        if (request.getCustomerEmail() == null || 
            !EMAIL_PATTERN.matcher(request.getCustomerEmail()).matches()) {
            request.addError(new ValidationError("customerEmail", 
                "Invalid email format", ErrorSeverity.ERROR));
        }
        
        if (request.getCustomerId() == null || request.getCustomerId().trim().isEmpty()) {
            request.addError(new ValidationError("customerId", 
                "Customer ID is required", ErrorSeverity.ERROR));
        }
        
        if (request.getMerchantId() == null || request.getMerchantId().trim().isEmpty()) {
            request.addError(new ValidationError("merchantId", 
                "Merchant ID is required", ErrorSeverity.ERROR));
        }
        
        // Validate payment method
        if (request.getPaymentMethod() == null) {
            request.addError(new ValidationError("paymentMethod", 
                "Payment method is required", ErrorSeverity.ERROR));
        }
        
        if (request.getPaymentToken() == null || request.getPaymentToken().trim().isEmpty()) {
            request.addError(new ValidationError("paymentToken", 
                "Payment token is required", ErrorSeverity.ERROR));
        }
        
        // Validate geographic data
        if (request.getBillingCountry() == null || request.getBillingCountry().length() != 2) {
            request.addError(new ValidationError("billingCountry", 
                "Invalid country code (ISO 3166-1 alpha-2)", ErrorSeverity.WARNING));
        }
        
        // Check for errors
        if (request.hasErrors()) {
            request.setState(ProcessingState.REJECTED);
            String errorMessages = request.getErrors().stream()
                .map(e -> e.getField() + ": " + e.getMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Validation failed");
            
            return PaymentResponse.failure(request.getTransactionId(), 
                "Validation failed: " + errorMessages);
        }
        
        request.setState(ProcessingState.VALIDATED);
        request.addAppliedRule("BASIC_VALIDATION_PASSED");
        
        logger.info("Validation passed for transaction: {}", request.getTransactionId());
        return PaymentResponse.success(request.getTransactionId(), "Validation passed");
    }
}
