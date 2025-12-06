package com.java.design.patterns.behavioral.chain.service;

import com.java.design.patterns.behavioral.chain.model.PaymentRequest;

public class AuditService {
    public void recordTransaction(PaymentRequest request) {
        // Dummy implementation
    }
    public void recordForReconciliation(PaymentRequest request) {
        // Dummy implementation
    }
    public void updateMerchantSettlement(PaymentRequest request) {
        // Dummy implementation
    }
    public void generateComplianceReport(PaymentRequest request) {
        // Dummy implementation
    }
}

