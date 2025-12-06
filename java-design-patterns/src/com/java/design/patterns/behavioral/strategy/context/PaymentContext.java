package com.java.design.patterns.behavioral.strategy.context;


import com.java.design.patterns.behavioral.strategy.exception.PaymentException;
import com.java.design.patterns.behavioral.strategy.model.PaymentRequest;
import com.java.design.patterns.behavioral.strategy.model.PaymentResponse;
import com.java.design.patterns.behavioral.strategy.strategy.PaymentStrategy;

public class PaymentContext {
    private PaymentStrategy paymentStrategy;
    
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    
    public PaymentResponse executePayment(PaymentRequest request) throws PaymentException {
        if (paymentStrategy == null) {
            throw new PaymentException("No payment strategy configured", "NO_STRATEGY");
        }
        
        if (!paymentStrategy.isAvailable()) {
            throw new PaymentException("Payment method is currently unavailable", 
                                      "SERVICE_UNAVAILABLE");
        }
        
        return paymentStrategy.processPayment(request);
    }
    
    public String getCurrentPaymentMethod() {
        return paymentStrategy != null ? paymentStrategy.getPaymentMethodName() : "NONE";
    }
}
