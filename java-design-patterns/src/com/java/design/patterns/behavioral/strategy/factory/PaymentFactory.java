package com.java.design.patterns.behavioral.strategy.factory;

import com.java.design.patterns.behavioral.strategy.exception.PaymentException;
import com.java.design.patterns.behavioral.strategy.strategy.PaymentStrategy;

import java.util.HashMap;
import java.util.Map;

public class PaymentFactory {
    
    private final Map<String, PaymentStrategy> strategies = new HashMap<>();
    
    public PaymentFactory(PaymentStrategy creditCardStrategy,
                         PaymentStrategy paypalStrategy,
                         PaymentStrategy bankTransferStrategy) {
        strategies.put("CREDIT_CARD", creditCardStrategy);
        strategies.put("PAYPAL", paypalStrategy);
        strategies.put("BANK_TRANSFER", bankTransferStrategy);
    }
    
    public PaymentStrategy getStrategy(String paymentMethod) throws PaymentException {
        PaymentStrategy strategy = strategies.get(paymentMethod.toUpperCase());
        if (strategy == null) {
            throw new PaymentException("Unsupported payment method: " + paymentMethod, 
                                      "UNSUPPORTED_METHOD");
        }
        return strategy;
    }
    
    public boolean isPaymentMethodSupported(String paymentMethod) {
        return strategies.containsKey(paymentMethod.toUpperCase());
    }
}
