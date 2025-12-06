package com.java.design.patterns.behavioral.state.state.implementation;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.exception.InvalidStateTransitionException;
import com.java.design.patterns.behavioral.state.model.OrderStatus;
import com.java.design.patterns.behavioral.state.state.OrderState;

public class ShippedState implements OrderState {

    @Override
    public void deliverOrder(OrderContext context) {
        System.out.println("Delivering order: " + context.getOrder().getOrderId());
        
        context.setState(new DeliveredState());
        context.setStatus(OrderStatus.DELIVERED);
        
        context.getNotificationService().sendEmail(
            context.getOrder().getCustomerId(),
            "Order Delivered",
            "Your order has been delivered successfully!"
        );
        
        context.getNotificationService().sendSMS(
            context.getOrder().getCustomerId(),
            "Your order " + context.getOrder().getOrderId() + " has been delivered!"
        );
    }

    @Override
    public void requestRefund(OrderContext context, String reason) {
        System.out.println("Refund requested for shipped order: " + 
                         context.getOrder().getOrderId());
        
        context.setState(new RefundRequestedState());
        context.setStatus(OrderStatus.REFUND_REQUESTED);
        
        context.getNotificationService().sendEmail(
            context.getOrder().getCustomerId(),
            "Refund Request Received",
            "Your refund request has been received and is being processed."
        );
    }

    @Override
    public void processPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException("Order is already paid and shipped.");
    }

    @Override
    public void confirmPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException("Order is already paid and shipped.");
    }

    @Override
    public void prepareOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException("Order is already shipped.");
    }

    @Override
    public void shipOrder(OrderContext context, String trackingNumber) 
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException("Order is already shipped.");
    }

    @Override
    public void cancelOrder(OrderContext context, String reason) 
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot cancel shipped order. Request refund instead.");
    }

    @Override
    public void processRefund(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException("No refund requested yet.");
    }

    @Override
    public String getStateName() {
        return "SHIPPED";
    }
}
