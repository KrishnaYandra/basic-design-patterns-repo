package com.java.design.patterns.behavioral.state.state;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.exception.InvalidStateTransitionException;

public interface OrderState {
    void processPayment(OrderContext context) throws InvalidStateTransitionException;
    void confirmPayment(OrderContext context) throws InvalidStateTransitionException;
    void prepareOrder(OrderContext context) throws InvalidStateTransitionException;
    void shipOrder(OrderContext context, String trackingNumber) throws InvalidStateTransitionException;
    void deliverOrder(OrderContext context) throws InvalidStateTransitionException;
    void cancelOrder(OrderContext context, String reason) throws InvalidStateTransitionException;
    void requestRefund(OrderContext context, String reason) throws InvalidStateTransitionException;
    void processRefund(OrderContext context) throws InvalidStateTransitionException;
    String getStateName();
}
