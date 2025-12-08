package com.java.design.patterns.behavioral.visitor.element.implementation;

import com.java.design.patterns.behavioral.visitor.element.Form;
import com.java.design.patterns.behavioral.visitor.visitor.FormOperation;

import java.math.BigDecimal;

public class PolicySelectionForm implements Form {
    private String coverageType;  // Comprehensive, Third-party, etc.
    private BigDecimal coverageAmount;
    private int deductibleAmount;
    private boolean includeRoadside;
    private boolean includeRental;
    private int policyDurationMonths;
    
    public PolicySelectionForm(String coverageType, BigDecimal coverageAmount,
                               int deductibleAmount, boolean includeRoadside,
                               boolean includeRental, int policyDurationMonths) {
        this.coverageType = coverageType;
        this.coverageAmount = coverageAmount;
        this.deductibleAmount = deductibleAmount;
        this.includeRoadside = includeRoadside;
        this.includeRental = includeRental;
        this.policyDurationMonths = policyDurationMonths;
    }
    
    @Override
    public void doOperation(FormOperation formOperation) {
        formOperation.doOperation(this);
    }
    
    // Getters
    public String getCoverageType() { return coverageType; }
    public BigDecimal getCoverageAmount() { return coverageAmount; }
    public int getDeductibleAmount() { return deductibleAmount; }
    public boolean isIncludeRoadside() { return includeRoadside; }
    public boolean isIncludeRental() { return includeRental; }
    public int getPolicyDurationMonths() { return policyDurationMonths; }
}
