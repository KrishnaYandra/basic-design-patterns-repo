package com.java.design.patterns.behavioral.state.state.implementation;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.exception.InvalidStateTransitionException;
import com.java.design.patterns.behavioral.state.model.OrderStatus;
import com.java.design.patterns.behavioral.state.state.OrderState;

public class PendingPaymentState implements OrderState {

    @Override
    public void processPayment(OrderContext context) throws InvalidStateTransitionException {
        System.out.println("Processing payment for order: " + context.getOrder().getOrderId());
        
        // Transition to payment processing state
        context.setState(new PaymentProcessingState());
        context.setStatus(OrderStatus.PAYMENT_PROCESSING);
        
        // Notify customer
        context.getNotificationService().sendEmail(
            context.getOrder().getCustomerId(),
            "Payment Processing",
            "Your payment is being processed."
        );
    }

    @Override
    public void cancelOrder(OrderContext context, String reason) {
        System.out.println("Cancelling order: " + context.getOrder().getOrderId());
        context.getOrder().setCancellationReason(reason);
        context.setState(new CancelledState());
        context.setStatus(OrderStatus.CANCELLED);
        
        context.getNotificationService().sendEmail(
            context.getOrder().getCustomerId(),
            "Order Cancelled",
            "Your order has been cancelled. Reason: " + reason
        );
    }

    @Override
    public void confirmPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot confirm payment from PENDING_PAYMENT state. Process payment first.");
    }

    @Override
    public void prepareOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot prepare order from PENDING_PAYMENT state.");
    }

    @Override
    public void shipOrder(OrderContext context, String trackingNumber) 
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot ship order from PENDING_PAYMENT state.");
    }

    @Override
    public void deliverOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot deliver order from PENDING_PAYMENT state.");
    }

    @Override
    public void requestRefund(OrderContext context, String reason) 
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot request refund from PENDING_PAYMENT state.");
    }

    @Override
    public void processRefund(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot process refund from PENDING_PAYMENT state.");
    }

    @Override
    public String getStateName() {
        return "PENDING_PAYMENT";
    }
}
