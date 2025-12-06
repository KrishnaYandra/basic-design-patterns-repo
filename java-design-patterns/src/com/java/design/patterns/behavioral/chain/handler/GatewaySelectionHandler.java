// 6. GatewaySelectionHandler.java - Dynamic routing to optimal gateway
package com.java.design.patterns.behavioral.chain.handler;

import com.java.design.patterns.behavioral.chain.model.GatewayRoutingMetadata;
import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import com.java.design.patterns.behavioral.chain.model.PaymentResponse;
import com.java.design.patterns.behavioral.chain.model.ProcessingState;
import com.java.design.patterns.behavioral.chain.service.GatewaySelectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewaySelectionHandler extends PaymentHandler {
    
    private final GatewaySelectionService gatewayService;
    private static final Logger logger = LoggerFactory.getLogger(GatewaySelectionHandler.class);

    public GatewaySelectionHandler(GatewaySelectionService gatewayService) {
        super("GatewaySelectionHandler");
        this.gatewayService = gatewayService;
    }
    
    @Override
    protected PaymentResponse process(PaymentRequest request) {
        logger.info("Selecting optimal payment gateway...");
        
        // Select gateway based on multiple factors:
        // - Payment method
        // - Country
        // - Currency
        // - Transaction amount
        // - Historical success rates
        // - Gateway fees
        // - Current gateway health/latency
        
        String selectedGateway = gatewayService.selectOptimalGateway(request);
        
        if (selectedGateway == null) {
            logger.error("No available gateway for transaction {}", request.getTransactionId());
            
            return PaymentResponse.failure(request.getTransactionId(),
                "No available payment gateway for this transaction");
        }
        
        request.setSelectedGateway(selectedGateway);
        request.addAppliedRule("GATEWAY_SELECTED: " + selectedGateway);
        request.addMetadata("selectedGateway", selectedGateway);
        
        // Add gateway-specific routing metadata
        GatewayRoutingMetadata metadata = gatewayService.getGatewayMetadata(selectedGateway);
        request.addMetadata("gatewaySuccessRate", metadata.getSuccessRate());
        request.addMetadata("gatewayFeePercentage", metadata.getFeePercentage());
        request.addMetadata("gatewayAvgLatency", metadata.getAvgLatencyMs());
        
        logger.info("Selected gateway {} for transaction {} (Success rate: {}%, Fee: {}%)", 
            selectedGateway, 
            request.getTransactionId(),
            metadata.getSuccessRate(),
            metadata.getFeePercentage());
        
        request.setState(ProcessingState.GATEWAY_SELECTED);
        return PaymentResponse.success(request.getTransactionId(), 
            "Gateway selected: " + selectedGateway);
    }
}
