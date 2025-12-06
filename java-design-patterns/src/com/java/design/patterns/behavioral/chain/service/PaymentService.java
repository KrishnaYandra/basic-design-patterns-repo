// PaymentService.java - Orchestrates the entire chain
package com.java.design.patterns.behavioral.chain.service;

import com.java.design.patterns.behavioral.chain.handler.*;
import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import com.java.design.patterns.behavioral.chain.model.PaymentResponse;

public class PaymentService {
    
    private final PaymentHandler chainHead;
    
    public PaymentService(
            FraudDetectionService fraudService,
            ComplianceService complianceService,
            RiskAssessmentService riskService,
            CurrencyConversionService currencyService,
            GatewaySelectionService gatewaySelectionService,
            PaymentGatewayService paymentGatewayService,
            AuditService auditService) {
        
        // Build the chain
        PaymentHandler validationHandler = new ValidationHandler();
        PaymentHandler fraudHandler = new FraudDetectionHandler(fraudService);
        PaymentHandler complianceHandler = new ComplianceHandler(complianceService);
        PaymentHandler riskHandler = new RiskAssessmentHandler(riskService);
        PaymentHandler currencyHandler = new CurrencyConversionHandler(currencyService);
        PaymentHandler gatewaySelectionHandler = new GatewaySelectionHandler(gatewaySelectionService);
        PaymentHandler processingHandler = new PaymentProcessingHandler(paymentGatewayService);
        PaymentHandler auditHandler = new AuditAndReconciliationHandler(auditService);
        
        // Chain them together
        validationHandler.setNext(fraudHandler);
        fraudHandler.setNext(complianceHandler);
        complianceHandler.setNext(riskHandler);
        riskHandler.setNext(currencyHandler);
        currencyHandler.setNext(gatewaySelectionHandler);
        gatewaySelectionHandler.setNext(processingHandler);
        processingHandler.setNext(auditHandler);
        
        this.chainHead = validationHandler;
    }
    
    public PaymentResponse processPayment(PaymentRequest request) {
        return chainHead.handle(request);
    }
}
