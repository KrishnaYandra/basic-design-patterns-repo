// 4. RiskAssessmentHandler.java - Business risk evaluation
package com.java.design.patterns.behavioral.chain.handler;

import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import com.java.design.patterns.behavioral.chain.model.PaymentResponse;
import com.java.design.patterns.behavioral.chain.model.ProcessingState;
import com.java.design.patterns.behavioral.chain.service.RiskAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class RiskAssessmentHandler extends PaymentHandler {
    
    private final RiskAssessmentService riskService;
    private static final BigDecimal HIGH_RISK_THRESHOLD = new BigDecimal("80.0");
    
    public RiskAssessmentHandler(RiskAssessmentService riskService) {
        super("RiskAssessmentHandler");
        this.riskService = riskService;
    }
    
    @Override
    protected PaymentResponse process(PaymentRequest request) {
        logger.info("Performing risk assessment...");
        
        // Calculate comprehensive risk score
        BigDecimal riskScore = riskService.calculateRiskScore(request);
        request.setRiskScore(riskScore);
        
        logger.info("Risk score for transaction {}: {}", 
            request.getTransactionId(), riskScore);
        
        // Check merchant risk profile
        String merchantRiskLevel = riskService.getMerchantRiskLevel(request.getMerchantId());
        request.addMetadata("merchantRiskLevel", merchantRiskLevel);
        
        // Check customer history
        boolean isFirstTimeCustomer = riskService.isFirstTimeCustomer(request.getCustomerId());
        if (isFirstTimeCustomer && request.getAmount().compareTo(new BigDecimal("500")) > 0) {
            request.addMetadata("riskFactor", "HIGH_VALUE_FIRST_TRANSACTION");
            riskScore = riskScore.add(new BigDecimal("15.0"));
        }
        
        // Check billing/shipping mismatch
        if (request.getBillingCountry() != null && request.getShippingCountry() != null &&
            !request.getBillingCountry().equals(request.getShippingCountry())) {
            request.addMetadata("riskFactor", "BILLING_SHIPPING_COUNTRY_MISMATCH");
            riskScore = riskScore.add(new BigDecimal("10.0"));
        }
        
        // High risk transaction
        if (riskScore.compareTo(HIGH_RISK_THRESHOLD) >= 0) {
            request.setState(ProcessingState.REJECTED);
            request.addAppliedRule("HIGH_RISK_REJECTION");
            
            logger.warn("Transaction {} rejected due to high risk score: {}", 
                request.getTransactionId(), riskScore);
            
            return PaymentResponse.failure(request.getTransactionId(),
                "Transaction rejected: High risk assessment");
        }
        
        request.setState(ProcessingState.RISK_ASSESSED);
        request.addAppliedRule("RISK_ASSESSMENT_PASSED");
        
        logger.info("Risk assessment passed for transaction: {}", request.getTransactionId());
        return PaymentResponse.success(request.getTransactionId(), "Risk assessment passed");
    }
}
