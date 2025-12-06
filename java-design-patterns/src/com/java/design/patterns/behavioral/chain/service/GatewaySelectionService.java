package com.java.design.patterns.behavioral.chain.service;

import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import com.java.design.patterns.behavioral.chain.model.GatewayRoutingMetadata;

public class GatewaySelectionService {
    public String selectOptimalGateway(PaymentRequest request) {
        // Dummy implementation: always return "DefaultGateway"
        return "DefaultGateway";
    }

    public GatewayRoutingMetadata getGatewayMetadata(String gatewayName) {
        // Dummy implementation: returns static metadata
        GatewayRoutingMetadata metadata = new GatewayRoutingMetadata(new java.math.BigDecimal("98.5"), new java.math.BigDecimal("2.0"), 120L);
        return metadata;
    }
}
