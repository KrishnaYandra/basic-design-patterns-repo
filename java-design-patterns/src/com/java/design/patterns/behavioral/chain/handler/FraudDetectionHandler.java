// 2. FraudDetectionHandler.java - ML-powered fraud detection
package com.java.design.patterns.behavioral.chain.handler;

import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import com.java.design.patterns.behavioral.chain.model.PaymentResponse;
import com.java.design.patterns.behavioral.chain.model.ProcessingState;
import com.java.design.patterns.behavioral.chain.service.FraudDetectionService;

import java.math.BigDecimal;

public class FraudDetectionHandler extends PaymentHandler {
    
    private final FraudDetectionService fraudService;
    private static final BigDecimal FRAUD_THRESHOLD = new BigDecimal("75.0");
    private static final BigDecimal REVIEW_THRESHOLD = new BigDecimal("60.0");
    
    public FraudDetectionHandler(FraudDetectionService fraudService) {
        super("FraudDetectionHandler");
        this.fraudService = fraudService;
    }
    
    @Override
    protected PaymentResponse process(PaymentRequest request) {
        logger.info("Running fraud detection analysis...");
        
        // Calculate fraud score using ML model (simulated)
        BigDecimal fraudScore = fraudService.calculateFraudScore(request);
        request.setFraudScore(fraudScore);
        
        logger.info("Fraud score for transaction {}: {}", 
            request.getTransactionId(), fraudScore);
        
        // High fraud risk - reject immediately
        if (fraudScore.compareTo(FRAUD_THRESHOLD) >= 0) {
            request.setState(ProcessingState.REJECTED);
            request.addAppliedRule("FRAUD_HIGH_RISK_REJECTION");
            
            logger.warn("Transaction {} rejected due to high fraud score: {}", 
                request.getTransactionId(), fraudScore);
            
            return PaymentResponse.failure(request.getTransactionId(),
                "Transaction rejected: High fraud risk detected");
        }
        
        // Medium fraud risk - hold for manual review
        if (fraudScore.compareTo(REVIEW_THRESHOLD) >= 0) {
            request.setState(ProcessingState.HELD_FOR_REVIEW);
            request.addAppliedRule("FRAUD_MANUAL_REVIEW_REQUIRED");
            request.addMetadata("reviewReason", "Elevated fraud score: " + fraudScore);
            
            logger.info("Transaction {} held for manual review. Fraud score: {}", 
                request.getTransactionId(), fraudScore);
            
            return PaymentResponse.failure(request.getTransactionId(),
                "Transaction held for manual review");
        }
        
        // Additional fraud checks
        if (fraudService.isVelocityCheckFailed(request)) {
            request.setState(ProcessingState.REJECTED);
            request.addAppliedRule("VELOCITY_CHECK_FAILED");
            return PaymentResponse.failure(request.getTransactionId(),
                "Too many transactions in short time period");
        }
        
        if (fraudService.isBlacklistedIp(request.getCustomerIpAddress())) {
            request.setState(ProcessingState.REJECTED);
            request.addAppliedRule("IP_BLACKLISTED");
            return PaymentResponse.failure(request.getTransactionId(),
                "Transaction from blacklisted IP address");
        }
        
        request.setState(ProcessingState.FRAUD_CHECKED);
        request.addAppliedRule("FRAUD_CHECK_PASSED");
        
        logger.info("Fraud detection passed for transaction: {}", request.getTransactionId());
        return PaymentResponse.success(request.getTransactionId(), "Fraud check passed");
    }
}
