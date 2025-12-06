package com.java.design.patterns.behavioral.chain.service;

import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import com.java.design.patterns.behavioral.chain.model.GatewayResponse;

public class PaymentGatewayService {
    public GatewayResponse executePayment(PaymentRequest request) {
        // Dummy implementation: always succeed
        return new GatewayResponse(true, "Payment executed successfully");
    }
}

