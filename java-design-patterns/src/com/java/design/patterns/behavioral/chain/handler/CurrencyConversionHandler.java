// 5. CurrencyConversionHandler.java - Multi-currency support
package com.java.design.patterns.behavioral.chain.handler;

import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import com.java.design.patterns.behavioral.chain.model.PaymentResponse;
import com.java.design.patterns.behavioral.chain.model.ProcessingState;
import com.java.design.patterns.behavioral.chain.service.CurrencyConversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConversionHandler extends PaymentHandler {
    
    private final CurrencyConversionService currencyService;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionHandler.class);

    public CurrencyConversionHandler(CurrencyConversionService currencyService) {
        super("CurrencyConversionHandler");
        this.currencyService = currencyService;
    }
    
    @Override
    protected PaymentResponse process(PaymentRequest request) {
        logger.info("Processing currency conversion...");
        
        String sourceCurrency = request.getCurrency();
        String targetCurrency = request.getTargetCurrency();
        
        // If no target currency specified or same as source, skip conversion
        if (targetCurrency == null || sourceCurrency.equals(targetCurrency)) {
            request.setConvertedAmount(request.getAmount());
            request.setExchangeRate(BigDecimal.ONE);
            request.addAppliedRule("NO_CURRENCY_CONVERSION_NEEDED");
            
            logger.info("No currency conversion needed for transaction: {}", 
                request.getTransactionId());
            
            request.setState(ProcessingState.CURRENCY_CONVERTED);
            return PaymentResponse.success(request.getTransactionId(), 
                "Currency conversion skipped");
        }
        
        // Get real-time exchange rate
        BigDecimal exchangeRate = currencyService.getExchangeRate(sourceCurrency, targetCurrency);
        
        if (exchangeRate == null) {
            logger.error("Unable to fetch exchange rate for {} to {}", 
                sourceCurrency, targetCurrency);
            
            return PaymentResponse.failure(request.getTransactionId(),
                "Currency conversion failed: Exchange rate unavailable");
        }
        
        // Calculate converted amount
        BigDecimal convertedAmount = request.getAmount()
            .multiply(exchangeRate)
            .setScale(2, RoundingMode.HALF_UP);
        
        request.setExchangeRate(exchangeRate);
        request.setConvertedAmount(convertedAmount);
        request.addAppliedRule("CURRENCY_CONVERTED");
        request.addMetadata("exchangeRate", exchangeRate);
        request.addMetadata("originalAmount", request.getAmount());
        request.addMetadata("originalCurrency", sourceCurrency);
        
        logger.info("Currency converted for transaction {}: {} {} -> {} {} (rate: {})", 
            request.getTransactionId(), 
            request.getAmount(), sourceCurrency,
            convertedAmount, targetCurrency,
            exchangeRate);
        
        request.setState(ProcessingState.CURRENCY_CONVERTED);
        return PaymentResponse.success(request.getTransactionId(), "Currency converted");
    }
}
