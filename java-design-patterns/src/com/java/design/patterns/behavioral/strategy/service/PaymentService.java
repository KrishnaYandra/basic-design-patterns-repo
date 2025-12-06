package com.java.design.patterns.behavioral.strategy.service;


import com.java.design.patterns.behavioral.strategy.context.PaymentContext;
import com.java.design.patterns.behavioral.strategy.exception.PaymentException;
import com.java.design.patterns.behavioral.strategy.factory.PaymentFactory;
import com.java.design.patterns.behavioral.strategy.model.PaymentRequest;
import com.java.design.patterns.behavioral.strategy.model.PaymentResponse;

public class PaymentService {

    private final PaymentContext paymentContext;
    private final PaymentFactory paymentFactory;

    // Constructor injection without Spring
    public PaymentService(PaymentContext paymentContext, PaymentFactory paymentFactory) {
        this.paymentContext = paymentContext;
        this.paymentFactory = paymentFactory;
    }

    public PaymentResponse processPayment(String paymentMethod, PaymentRequest request)
            throws PaymentException {
        var strategy = paymentFactory.getStrategy(paymentMethod);
        paymentContext.setPaymentStrategy(strategy);
        return paymentContext.executePayment(request);
    }

    public boolean isPaymentMethodAvailable(String paymentMethod) {
        try {
            var strategy = paymentFactory.getStrategy(paymentMethod);
            return strategy.isAvailable();
        } catch (PaymentException e) {
            return false;
        }
    }
}