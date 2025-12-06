// 3. ComplianceHandler.java - AML, GDPR, PCI-DSS, Sanctions
package com.java.design.patterns.behavioral.chain.handler;


import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import com.java.design.patterns.behavioral.chain.model.PaymentResponse;
import com.java.design.patterns.behavioral.chain.model.ComplianceCheckResult;
import com.java.design.patterns.behavioral.chain.model.ProcessingState;
import com.java.design.patterns.behavioral.chain.service.ComplianceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class ComplianceHandler extends PaymentHandler {
    
    private final ComplianceService complianceService;
    private static final BigDecimal AML_THRESHOLD = new BigDecimal("10000.00");
    
    public ComplianceHandler(ComplianceService complianceService) {
        super("ComplianceHandler");
        this.complianceService = complianceService;
    }
    
    @Override
    protected PaymentResponse process(PaymentRequest request) {
        logger.info("Running compliance checks (AML, GDPR, PCI-DSS, Sanctions)...");
        
        ComplianceCheckResult result = new ComplianceCheckResult();
        
        // 1. Anti-Money Laundering (AML) Check
        if (request.getAmount().compareTo(AML_THRESHOLD) >= 0) {
            boolean amlPassed = complianceService.performAMLCheck(request);
            result.setAmlPassed(amlPassed);
            
            if (!amlPassed) {
                result.addFlag("AML_CHECK_FAILED");
                result.setRiskLevel("CRITICAL");
            } else {
                request.addAppliedRule("AML_CHECK_PASSED");
            }
        } else {
            result.setAmlPassed(true);
            request.addAppliedRule("AML_CHECK_NOT_REQUIRED");
        }
        
        // 2. GDPR Compliance (EU customers)
        if (complianceService.isEUCustomer(request.getBillingCountry())) {
            boolean gdprCompliant = complianceService.checkGDPRCompliance(request);
            result.setGdprCompliant(gdprCompliant);
            
            if (!gdprCompliant) {
                result.addFlag("GDPR_CONSENT_MISSING");
            } else {
                request.addAppliedRule("GDPR_COMPLIANT");
            }
        } else {
            result.setGdprCompliant(true);
        }
        
        // 3. PCI-DSS Compliance
        boolean pciCompliant = complianceService.validatePCIDSS(request);
        result.setPciDssCompliant(pciCompliant);
        
        if (!pciCompliant) {
            result.addFlag("PCI_DSS_VIOLATION");
            result.setRiskLevel("CRITICAL");
        } else {
            request.addAppliedRule("PCI_DSS_COMPLIANT");
        }
        
        // 4. Sanctions Screening (OFAC, EU, UN lists)
        boolean sanctionsPassed = complianceService.checkSanctionsList(
            request.getCustomerId(), 
            request.getBillingCountry()
        );
        result.setSanctionsCheckPassed(sanctionsPassed);
        
        if (!sanctionsPassed) {
            result.addFlag("SANCTIONED_ENTITY");
            result.setRiskLevel("CRITICAL");
        } else {
            request.addAppliedRule("SANCTIONS_CHECK_PASSED");
        }
        
        // 5. Country-specific regulations
        if (!complianceService.checkCountryRegulations(request)) {
            result.addFlag("COUNTRY_REGULATION_VIOLATION");
        }
        
        request.setComplianceResult(result);
        
        // Fail if any critical compliance check failed
        if (!result.isFullyCompliant()) {
            request.setState(ProcessingState.REJECTED);
            
            String failureReasons = String.join(", ", result.getFlags());
            logger.error("Compliance checks failed for transaction {}: {}", 
                request.getTransactionId(), failureReasons);
            
            return PaymentResponse.failure(request.getTransactionId(),
                "Compliance checks failed: " + failureReasons);
        }
        
        request.setState(ProcessingState.COMPLIANCE_APPROVED);
        logger.info("All compliance checks passed for transaction: {}", 
            request.getTransactionId());
        
        return PaymentResponse.success(request.getTransactionId(), "Compliance approved");
    }
}
