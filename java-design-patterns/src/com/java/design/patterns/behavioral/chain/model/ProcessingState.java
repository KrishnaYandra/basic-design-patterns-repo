package com.java.design.patterns.behavioral.chain.model;

public enum ProcessingState {
    PENDING,
    VALIDATED,
    FRAUD_CHECKED,
    COMPLIANCE_APPROVED,
    RISK_ASSESSED,
    CURRENCY_CONVERTED,
    GATEWAY_SELECTED,
    PROCESSING,
    COMPLETED,
    FAILED,
    REJECTED,
    REQUIRES_3DS, // 3D Secure authentication needed
    HELD_FOR_REVIEW
}