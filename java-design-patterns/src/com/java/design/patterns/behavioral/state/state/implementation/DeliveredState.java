package com.java.design.patterns.behavioral.state.state.implementation;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.exception.InvalidStateTransitionException;
import com.java.design.patterns.behavioral.state.model.ShippingDetails;
import com.java.design.patterns.behavioral.state.state.OrderState;

import java.time.LocalDateTime;

public class DeliveredState implements OrderState {

    @Override
    public void deliverOrder(OrderContext context) {
        // Idempotent: already delivered
        System.out.println("Order " + context.getOrder().getOrderId()
                + " is already in DELIVERED state.");

        ShippingDetails shippingDetails = context.getOrder().getShippingDetails();
        if (shippingDetails != null && shippingDetails.getDeliveredAt() == null) {
            shippingDetails.setDeliveredAt(LocalDateTime.now());
        }
    }

    @Override
    public void processPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot process payment. Order is already delivered.");
    }

    @Override
    public void confirmPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot confirm payment. Order is already delivered.");
    }

    @Override
    public void prepareOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot prepare order. Order is already delivered.");
    }

    @Override
    public void shipOrder(OrderContext context, String trackingNumber)
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot ship order. Order is already delivered.");
    }

    @Override
    public void cancelOrder(OrderContext context, String reason)
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot cancel. Order is already delivered and completed.");
    }

    @Override
    public void requestRefund(OrderContext context, String reason)
            throws InvalidStateTransitionException {
        // Depending on business rules, you could allow this to transition
        // to RefundRequestedState; here we treat Delivered as final.
        throw new InvalidStateTransitionException(
                "Refund cannot be requested from DELIVERED state in this workflow.");
    }

    @Override
    public void processRefund(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "No refund workflow allowed from DELIVERED state.");
    }

    @Override
    public String getStateName() {
        return "DELIVERED";
    }
}
