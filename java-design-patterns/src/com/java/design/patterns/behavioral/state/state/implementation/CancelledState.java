package com.java.design.patterns.behavioral.state.state.implementation;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.exception.InvalidStateTransitionException;
import com.java.design.patterns.behavioral.state.state.OrderState;

public class CancelledState implements OrderState {

    @Override
    public void processPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot process payment. Order is cancelled.");
    }

    @Override
    public void confirmPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot confirm payment. Order is cancelled.");
    }

    @Override
    public void prepareOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot prepare order. Order is cancelled.");
    }

    @Override
    public void shipOrder(OrderContext context, String trackingNumber)
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot ship order. Order is cancelled.");
    }

    @Override
    public void deliverOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot deliver order. Order is cancelled.");
    }

    @Override
    public void cancelOrder(OrderContext context, String reason)
            throws InvalidStateTransitionException {
        // Idempotent behaviour: already cancelled
        System.out.println("Order " + context.getOrder().getOrderId()
                + " is already in CANCELLED state. Reason: "
                + context.getOrder().getCancellationReason());
    }

    @Override
    public void requestRefund(OrderContext context, String reason)
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Refund cannot be requested. Order is cancelled without refund.");
    }

    @Override
    public void processRefund(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "No refund workflow for a cancelled order in this state.");
    }

    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}
