package com.java.design.patterns.behavioral.chain.service;

import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import java.math.BigDecimal;

public class RiskAssessmentService {
    public BigDecimal calculateRiskScore(PaymentRequest request) {
        // Dummy implementation: always return 50.0
        return new BigDecimal("50.0");
    }
    public String getMerchantRiskLevel(String merchantId) {
        // Dummy implementation: always return "LOW"
        return "LOW";
    }
    public boolean isFirstTimeCustomer(String customerId) {
        // Dummy implementation: always return false
        return false;
    }
}

