package com.java.design.patterns.behavioral.chain.model;

import java.util.ArrayList;
import java.util.List;

public class ComplianceCheckResult {
    private boolean amlPassed;
    private boolean gdprCompliant;
    private boolean pciDssCompliant;
    private boolean sanctionsCheckPassed;
    private List<String> flags;
    private String riskLevel; // LOW, MEDIUM, HIGH, CRITICAL
    
    public ComplianceCheckResult() {
        this.flags = new ArrayList<>();
    }
    
    // Getters and setters
    public boolean isAmlPassed() { return amlPassed; }
    public void setAmlPassed(boolean amlPassed) { this.amlPassed = amlPassed; }
    
    public boolean isGdprCompliant() { return gdprCompliant; }
    public void setGdprCompliant(boolean gdprCompliant) { this.gdprCompliant = gdprCompliant; }
    
    public boolean isPciDssCompliant() { return pciDssCompliant; }
    public void setPciDssCompliant(boolean pciDssCompliant) { this.pciDssCompliant = pciDssCompliant; }
    
    public boolean isSanctionsCheckPassed() { return sanctionsCheckPassed; }
    public void setSanctionsCheckPassed(boolean sanctionsCheckPassed) { 
        this.sanctionsCheckPassed = sanctionsCheckPassed; 
    }
    
    public List<String> getFlags() { return flags; }
    public void addFlag(String flag) { this.flags.add(flag); }
    
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    
    public boolean isFullyCompliant() {
        return amlPassed && gdprCompliant && pciDssCompliant && sanctionsCheckPassed;
    }
}