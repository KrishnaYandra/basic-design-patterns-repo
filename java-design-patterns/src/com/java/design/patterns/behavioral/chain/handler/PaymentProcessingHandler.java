// 7. PaymentProcessingHandler.java - Actual payment execution
package com.java.design.patterns.behavioral.chain.handler;

import com.java.design.patterns.behavioral.chain.model.GatewayResponse;
import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import com.java.design.patterns.behavioral.chain.model.PaymentResponse;
import com.java.design.patterns.behavioral.chain.model.ProcessingState;
import com.java.design.patterns.behavioral.chain.service.PaymentGatewayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentProcessingHandler extends PaymentHandler {
    
    private final PaymentGatewayService gatewayService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessingHandler.class);

    public PaymentProcessingHandler(PaymentGatewayService gatewayService) {
        super("PaymentProcessingHandler");
        this.gatewayService = gatewayService;
    }
    
    @Override
    protected PaymentResponse process(PaymentRequest request) {
        logger.info("Processing payment through gateway: {}", request.getSelectedGateway());
        
        request.setState(ProcessingState.PROCESSING);
        
        try {
            // Execute payment through selected gateway
            GatewayResponse gatewayResponse = gatewayService.executePayment(request);
            
            if (gatewayResponse.isSuccess()) {
                request.setState(ProcessingState.COMPLETED);
                request.addAppliedRule("PAYMENT_SUCCESSFUL");
                
                PaymentResponse response = PaymentResponse.success(
                    request.getTransactionId(), 
                    "Payment processed successfully"
                );
                response.setGatewayTransactionId(gatewayResponse.getGatewayTransactionId());
                response.setFinalState(ProcessingState.COMPLETED);
                
                logger.info("Payment successful for transaction {}, Gateway TX ID: {}", 
                    request.getTransactionId(), 
                    gatewayResponse.getGatewayTransactionId());
                
                return response;
                
            } else {
                request.setState(ProcessingState.FAILED);
                request.addAppliedRule("PAYMENT_FAILED");
                
                logger.error("Payment failed for transaction {}: {}", 
                    request.getTransactionId(), 
                    gatewayResponse.getErrorMessage());
                
                return PaymentResponse.failure(request.getTransactionId(),
                    "Payment failed: " + gatewayResponse.getErrorMessage());
            }
            
        } catch (Exception e) {
            request.setState(ProcessingState.FAILED);
            logger.error("Exception during payment processing for transaction {}: {}", 
                request.getTransactionId(), e.getMessage(), e);
            
            return PaymentResponse.failure(request.getTransactionId(),
                "Payment processing error: " + e.getMessage());
        }
    }
}
