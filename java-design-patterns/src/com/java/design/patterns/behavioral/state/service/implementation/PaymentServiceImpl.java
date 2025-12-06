package com.java.design.patterns.behavioral.state.service.implementation;

import com.java.design.patterns.behavioral.state.model.PaymentDetails;
import com.java.design.patterns.behavioral.state.service.PaymentService;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public boolean processPayment(PaymentDetails paymentDetails) {
        System.out.println("[PaymentService] Processing payment: " + paymentDetails);
        // Dummy logic – always succeeds
        return true;
    }

    @Override
    public void refundPayment(PaymentDetails paymentDetails) {
        System.out.println("[PaymentService] Refunding payment: " + paymentDetails);
    }

    @Override
    public void initiateRefund(PaymentDetails paymentDetails) {
        System.out.println("[PaymentService] Initiating refund workflow for: " + paymentDetails);
    }
}
