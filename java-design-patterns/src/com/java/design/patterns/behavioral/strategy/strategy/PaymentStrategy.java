package com.java.design.patterns.behavioral.strategy.strategy;

import com.java.design.patterns.behavioral.strategy.exception.PaymentException;
import com.java.design.patterns.behavioral.strategy.model.PaymentRequest;
import com.java.design.patterns.behavioral.strategy.model.PaymentResponse;

/**
 * Strategy interface for payment processing
 */
public interface PaymentStrategy {
    PaymentResponse processPayment(PaymentRequest request) throws PaymentException;
    boolean isAvailable();
    String getPaymentMethodName();
    void validatePaymentDetails(PaymentRequest request) throws PaymentException;
}
