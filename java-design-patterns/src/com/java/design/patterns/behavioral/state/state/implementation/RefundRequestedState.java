package com.java.design.patterns.behavioral.state.state.implementation;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.exception.InvalidStateTransitionException;
import com.java.design.patterns.behavioral.state.model.OrderStatus;
import com.java.design.patterns.behavioral.state.state.OrderState;

public class RefundRequestedState implements OrderState {

    @Override
    public void requestRefund(OrderContext context, String reason) {
        // Idempotent: refund already requested
        System.out.println("Refund already requested for order: " 
                + context.getOrder().getOrderId());

        // Optionally append additional reason/info
        String existingReason = context.getOrder().getCancellationReason();
        if (existingReason == null || existingReason.isEmpty()) {
            context.getOrder().setCancellationReason(reason);
        } else {
            context.getOrder().setCancellationReason(existingReason + " | Extra: " + reason);
        }
    }

    @Override
    public void processRefund(OrderContext context) {
        System.out.println("Processing refund for order: " 
                + context.getOrder().getOrderId());

        // Call payment service to actually refund money
        context.getPaymentService().initiateRefund(
                context.getOrder().getPaymentDetails()
        );

        // Release inventory if your business supports restocking on return
        context.getInventoryService().releaseItems(context.getOrder().getItems());

        // Transition to REFUNDED state
        context.setState(new RefundedState());
        context.setStatus(OrderStatus.REFUNDED);

        // Notify customer
        context.getNotificationService().sendEmail(
                context.getOrder().getCustomerId(),
                "Refund Completed",
                "Your refund for order " + context.getOrder().getOrderId() 
                        + " has been processed successfully."
        );
    }

    @Override
    public void processPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot process payment. Refund has been requested for this order.");
    }

    @Override
    public void confirmPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot confirm payment. Refund has been requested for this order.");
    }

    @Override
    public void prepareOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot prepare order. Refund has been requested.");
    }

    @Override
    public void shipOrder(OrderContext context, String trackingNumber)
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot ship order. Refund has been requested.");
    }

    @Override
    public void deliverOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot deliver order. Refund has been requested.");
    }

    @Override
    public void cancelOrder(OrderContext context, String reason)
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
                "Cannot cancel order. It is already in refund-request workflow.");
    }

    @Override
    public String getStateName() {
        return "REFUND_REQUESTED";
    }
}
