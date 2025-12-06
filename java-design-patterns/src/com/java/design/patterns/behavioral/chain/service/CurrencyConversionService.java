package com.java.design.patterns.behavioral.chain.service;

import java.math.BigDecimal;

public class CurrencyConversionService {
    public BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency) {
        // Dummy implementation: always return 1.0
        return BigDecimal.ONE;
    }
}

