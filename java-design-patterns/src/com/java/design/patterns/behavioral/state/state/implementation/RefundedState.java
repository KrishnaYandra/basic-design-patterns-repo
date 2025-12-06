package com.java.design.patterns.behavioral.state.state.implementation;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.exception.InvalidStateTransitionException;
import com.java.design.patterns.behavioral.state.state.OrderState;

public class RefundedState implements OrderState {

    @Override
    public void processPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot process payment. Order is already refunded.");
    }

    @Override
    public void confirmPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot confirm payment. Order is already refunded.");
    }

    @Override
    public void prepareOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot prepare order. Order is already refunded.");
    }

    @Override
    public void shipOrder(OrderContext context, String trackingNumber)
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot ship order. Order is already refunded.");
    }

    @Override
    public void deliverOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot deliver order. Order is already refunded.");
    }

    @Override
    public void cancelOrder(OrderContext context, String reason)
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Order is already refunded and considered closed.");
    }

    @Override
    public void requestRefund(OrderContext context, String reason)
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Refund already processed. Duplicate refund requests are not allowed.");
    }

    @Override
    public void processRefund(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Refund already processed. No further refund operations allowed.");
    }

    @Override
    public String getStateName() {
        return "REFUNDED";
    }
}
