// PaymentHandler.java - Abstract base for all handlers
package com.java.design.patterns.behavioral.chain.handler;

import com.java.design.patterns.behavioral.chain.model.PaymentRequest;
import com.java.design.patterns.behavioral.chain.model.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PaymentHandler {
    protected static final Logger logger = LoggerFactory.getLogger(PaymentHandler.class);

    protected PaymentHandler nextHandler;
    private String handlerName;
    
    public PaymentHandler(String handlerName) {
        this.handlerName = handlerName;
    }
    
    public void setNext(PaymentHandler handler) {
        this.nextHandler = handler;
    }
    
    /**
     * Template method that handles timing and delegation
     */
    public PaymentResponse handle(PaymentRequest request) {
        long startTime = System.currentTimeMillis();
        
        try {
            logger.info("[{}] Processing transaction: {}", handlerName, request.getTransactionId());

            // Execute handler-specific logic
            PaymentResponse response = process(request);
            
            // Record processing time
            long processingTime = System.currentTimeMillis() - startTime;
            request.recordHandlerTime(handlerName, processingTime);
            
            // If this handler rejected/failed, stop the chain
            if (!response.isSuccess()) {
                logger.warn("[{}] Transaction {} rejected: {}",
                    handlerName, request.getTransactionId(), response.getMessage());
                return response;
            }
            
            // If there's a next handler, delegate
            if (nextHandler != null) {
                return nextHandler.handle(request);
            }
            
            // End of chain - success
            return response;
            
        } catch (Exception e) {
            logger.error("[{}] Error processing transaction {}: {}",
                handlerName, request.getTransactionId(), e.getMessage(), e);

            return PaymentResponse.failure(
                request.getTransactionId(),
                "System error in " + handlerName + ": " + e.getMessage()
            );
        }
    }
    
    /**
     * Abstract method each handler must implement
     */
    protected abstract PaymentResponse process(PaymentRequest request);
    
    protected String getHandlerName() {
        return handlerName;
    }
}
