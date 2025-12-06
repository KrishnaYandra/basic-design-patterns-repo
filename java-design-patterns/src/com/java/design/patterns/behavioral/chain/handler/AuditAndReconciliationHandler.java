// 8. AuditAndReconciliationHandler.java - Final logging and reconciliation
package com.java.design.patterns.behavioral.chain.handler;

import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import com.java.design.patterns.behavioral.chain.model.PaymentResponse;
import com.java.design.patterns.behavioral.chain.model.ProcessingState;
import com.java.design.patterns.behavioral.chain.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditAndReconciliationHandler extends PaymentHandler {
    
    private final AuditService auditService;
    private static final Logger logger = LoggerFactory.getLogger(AuditAndReconciliationHandler.class);

    public AuditAndReconciliationHandler(AuditService auditService) {
        super("AuditAndReconciliationHandler");
        this.auditService = auditService;
    }
    
    @Override
    protected PaymentResponse process(PaymentRequest request) {
        logger.info("Recording audit trail and reconciliation data...");
        
        try {
            // Save complete audit trail
            auditService.recordTransaction(request);
            
            // Record for reconciliation
            auditService.recordForReconciliation(request);
            
            // Update merchant settlement data
            auditService.updateMerchantSettlement(request);
            
            // Generate compliance report if needed
            if (request.getAmount().compareTo(java.math.BigDecimal.valueOf(10000)) >= 0) {
                auditService.generateComplianceReport(request);
            }
            
            logger.info("Audit and reconciliation completed for transaction: {}", 
                request.getTransactionId());
            
            request.addAppliedRule("AUDIT_RECORDED");
            
            return PaymentResponse.success(request.getTransactionId(), 
                "Audit and reconciliation completed");
                
        } catch (Exception e) {
            logger.error("Audit recording failed for transaction {}: {}", 
                request.getTransactionId(), e.getMessage(), e);
            
            // Don't fail the payment, just log the audit issue
            return PaymentResponse.success(request.getTransactionId(), 
                "Payment completed (audit recording issue logged)");
        }
    }
}
