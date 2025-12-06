// PaymentController.java
package com.java.design.patterns.behavioral.chain.controller;

import com.java.design.patterns.behavioral.chain.model.*;
import com.java.design.patterns.behavioral.chain.service.*;

import java.math.BigDecimal;

//@RestController
//@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //@PostMapping("/process")
    public PaymentResponse processPayment(/*@RequestBody*/ PaymentRequest request) {
        return paymentService.processPayment(request);
    }

    public static void main(String[] args) {
        // Instantiate all required services for PaymentService
        FraudDetectionService fraudService = new FraudDetectionService();
        ComplianceService complianceService = new ComplianceService();
        RiskAssessmentService riskService = new RiskAssessmentService();
        CurrencyConversionService currencyService = new CurrencyConversionService();
        GatewaySelectionService gatewaySelectionService = new GatewaySelectionService();
        PaymentGatewayService paymentGatewayService = new PaymentGatewayService();
        AuditService auditService = new AuditService();

        PaymentService paymentService = new PaymentService(
            fraudService,
            complianceService,
            riskService,
            currencyService,
            gatewaySelectionService,
            paymentGatewayService,
            auditService
        );
        PaymentController controller = new PaymentController(paymentService);

        // Create a dummy PaymentRequest
        PaymentRequest request = new PaymentRequest();
        request.setCustomerId("CUST-12345");
        request.setMerchantId("MERCH-67890");
        request.setAmount(new BigDecimal("1500.00"));
        request.setCurrency("USD");
        request.setTargetCurrency("EUR");
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        request.setPaymentToken("tok_visa_4242");
        request.setCustomerEmail("john.doe@example.com");
        request.setCustomerIpAddress("203.0.113.45");
        request.setDeviceFingerprint("fp_abc123xyz");
        request.setBillingCountry("US");
        request.setShippingCountry("DE");

        PaymentResponse response = controller.processPayment(request);
        System.out.println("Payment Response: " + response);
    }
}
