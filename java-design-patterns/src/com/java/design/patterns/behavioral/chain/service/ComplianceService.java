package com.java.design.patterns.behavioral.chain.service;

import com.java.design.patterns.behavioral.chain.model.PaymentRequest;

public class ComplianceService {
    public boolean performAMLCheck(PaymentRequest request) {
        // Dummy implementation: always pass
        return true;
    }
    public boolean isEUCustomer(String billingCountry) {
        // Dummy: treat 'DE', 'FR', 'IT', etc. as EU
        return billingCountry != null && java.util.Arrays.asList("DE","FR","IT","ES","NL","BE","AT","SE","DK","FI","PT","IE","GR","LU","CZ","PL","HU","SK","SI","EE","LV","LT","BG","RO","HR","CY","MT").contains(billingCountry);
    }
    public boolean checkGDPRCompliance(PaymentRequest request) {
        // Dummy: always true
        return true;
    }
    public boolean validatePCIDSS(PaymentRequest request) {
        // Dummy: always true
        return true;
    }
    public boolean checkSanctionsList(String customerId, String billingCountry) {
        // Dummy: always true
        return true;
    }
    public boolean checkCountryRegulations(PaymentRequest request) {
        // Dummy: always true
        return true;
    }
}
