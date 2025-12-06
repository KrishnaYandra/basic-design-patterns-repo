package com.java.design.patterns.behavioral.state.service;

import com.java.design.patterns.behavioral.state.model.PaymentDetails;

public interface PaymentService {
    boolean processPayment(PaymentDetails paymentDetails);
    void refundPayment(PaymentDetails paymentDetails);
    void initiateRefund(PaymentDetails paymentDetails);
}
