// PaymentRequest.java - The request object that travels through the chain
package com.java.design.patterns.behavioral.chain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class PaymentRequest {
    private String transactionId;
    private String customerId;
    private String merchantId;
    private BigDecimal amount;
    private String currency;
    private String targetCurrency;
    private PaymentMethod paymentMethod;
    private String paymentToken; // Tokenized card/account info
    
    // Customer details for fraud detection
    private String customerEmail;
    private String customerIpAddress;
    private String deviceFingerprint;
    private String billingCountry;
    private String shippingCountry;
    
    // Metadata
    private LocalDateTime requestTime;
    private Map<String, Object> metadata;
    
    // Processing state - enhanced as it moves through chain
    private ProcessingState state;
    private BigDecimal fraudScore; // 0-100
    private BigDecimal riskScore;  // 0-100
    private String selectedGateway;
    private BigDecimal convertedAmount;
    private BigDecimal exchangeRate;
    private List<ValidationError> errors;
    private List<String> appliedRules;
    private ComplianceCheckResult complianceResult;
    
    // Audit trail
    private Map<String, Long> handlerProcessingTimes;
    
    public PaymentRequest() {
        this.transactionId = UUID.randomUUID().toString();
        this.requestTime = LocalDateTime.now();
        this.metadata = new HashMap<>();
        this.errors = new ArrayList<>();
        this.appliedRules = new ArrayList<>();
        this.handlerProcessingTimes = new LinkedHashMap<>();
        this.state = ProcessingState.PENDING;
    }
    
    // Getters and setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getTargetCurrency() { return targetCurrency; }
    public void setTargetCurrency(String targetCurrency) { this.targetCurrency = targetCurrency; }
    
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getPaymentToken() { return paymentToken; }
    public void setPaymentToken(String paymentToken) { this.paymentToken = paymentToken; }
    
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    
    public String getCustomerIpAddress() { return customerIpAddress; }
    public void setCustomerIpAddress(String customerIpAddress) { this.customerIpAddress = customerIpAddress; }
    
    public String getDeviceFingerprint() { return deviceFingerprint; }
    public void setDeviceFingerprint(String deviceFingerprint) { this.deviceFingerprint = deviceFingerprint; }
    
    public String getBillingCountry() { return billingCountry; }
    public void setBillingCountry(String billingCountry) { this.billingCountry = billingCountry; }
    
    public String getShippingCountry() { return shippingCountry; }
    public void setShippingCountry(String shippingCountry) { this.shippingCountry = shippingCountry; }
    
    public LocalDateTime getRequestTime() { return requestTime; }
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }
    
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    
    public ProcessingState getState() { return state; }
    public void setState(ProcessingState state) { this.state = state; }
    
    public BigDecimal getFraudScore() { return fraudScore; }
    public void setFraudScore(BigDecimal fraudScore) { this.fraudScore = fraudScore; }
    
    public BigDecimal getRiskScore() { return riskScore; }
    public void setRiskScore(BigDecimal riskScore) { this.riskScore = riskScore; }
    
    public String getSelectedGateway() { return selectedGateway; }
    public void setSelectedGateway(String selectedGateway) { this.selectedGateway = selectedGateway; }
    
    public BigDecimal getConvertedAmount() { return convertedAmount; }
    public void setConvertedAmount(BigDecimal convertedAmount) { this.convertedAmount = convertedAmount; }
    
    public BigDecimal getExchangeRate() { return exchangeRate; }
    public void setExchangeRate(BigDecimal exchangeRate) { this.exchangeRate = exchangeRate; }
    
    public List<ValidationError> getErrors() { return errors; }
    public void addError(ValidationError error) { this.errors.add(error); }
    
    public List<String> getAppliedRules() { return appliedRules; }
    public void addAppliedRule(String rule) { this.appliedRules.add(rule); }
    
    public ComplianceCheckResult getComplianceResult() { return complianceResult; }
    public void setComplianceResult(ComplianceCheckResult complianceResult) { 
        this.complianceResult = complianceResult; 
    }
    
    public Map<String, Long> getHandlerProcessingTimes() { return handlerProcessingTimes; }
    public void recordHandlerTime(String handlerName, long milliseconds) {
        this.handlerProcessingTimes.put(handlerName, milliseconds);
    }
    
    public void addMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }
    
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
