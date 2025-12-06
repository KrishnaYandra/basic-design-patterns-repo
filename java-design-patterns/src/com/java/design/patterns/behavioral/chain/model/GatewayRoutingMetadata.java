package com.java.design.patterns.behavioral.chain.model;

import java.math.BigDecimal;

public class GatewayRoutingMetadata {
    private BigDecimal successRate;
    private BigDecimal feePercentage;
    private Long avgLatencyMs;
    
    public GatewayRoutingMetadata(BigDecimal successRate, BigDecimal feePercentage, Long avgLatencyMs) {
        this.successRate = successRate;
        this.feePercentage = feePercentage;
        this.avgLatencyMs = avgLatencyMs;
    }
    
    public BigDecimal getSuccessRate() { return successRate; }
    public BigDecimal getFeePercentage() { return feePercentage; }
    public Long getAvgLatencyMs() { return avgLatencyMs; }
}