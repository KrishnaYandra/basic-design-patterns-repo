package com.java.design.patterns.behavioral.chain.service;

import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import java.math.BigDecimal;

public class FraudDetectionService {
    public BigDecimal calculateFraudScore(PaymentRequest request) {
        // Dummy implementation: always return 50.0
        return new BigDecimal("50.0");
    }

    public boolean isVelocityCheckFailed(com.java.design.patterns.behavioral.chain.model.PaymentRequest request) {
        // Dummy: always false
        return false;
    }

    public boolean isBlacklistedIp(String ipAddress) {
        // Dummy: always false
        return false;
    }
}
