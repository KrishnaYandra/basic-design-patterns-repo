package com.java.design.patterns.behavioral.state.state.implementation;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.exception.InvalidStateTransitionException;
import com.java.design.patterns.behavioral.state.model.OrderStatus;
import com.java.design.patterns.behavioral.state.state.OrderState;

public class PaidState implements OrderState {

    @Override
    public void prepareOrder(OrderContext context) {
        System.out.println("Preparing order: " + context.getOrder().getOrderId());
        
        // Initiate warehouse preparation
        context.getInventoryService().initiateOrderPreparation(context.getOrder());
        
        context.setState(new PreparingState());
        context.setStatus(OrderStatus.PREPARING);
        
        context.getNotificationService().sendEmail(
            context.getOrder().getCustomerId(),
            "Order Preparing",
            "Your order is being prepared for shipment."
        );
    }

    @Override
    public void cancelOrder(OrderContext context, String reason) {
        System.out.println("Cancelling paid order: " + context.getOrder().getOrderId());
        
        // Release inventory
        context.getInventoryService().releaseItems(context.getOrder().getItems());
        
        // Initiate refund
        context.getPaymentService().initiateRefund(context.getOrder().getPaymentDetails());
        
        context.getOrder().setCancellationReason(reason);
        context.setState(new RefundedState());
        context.setStatus(OrderStatus.REFUNDED);
        
        context.getNotificationService().sendEmail(
            context.getOrder().getCustomerId(),
            "Order Cancelled & Refunded",
            "Your order has been cancelled and refund initiated."
        );
    }

    @Override
    public void processPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException("Order is already paid.");
    }

    @Override
    public void confirmPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException("Payment is already confirmed.");
    }

    @Override
    public void shipOrder(OrderContext context, String trackingNumber) 
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot ship order from PAID state. Prepare order first.");
    }

    @Override
    public void deliverOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException("Cannot deliver unprepared order.");
    }

    @Override
    public void requestRefund(OrderContext context, String reason) 
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Use cancelOrder to cancel and refund from PAID state.");
    }

    @Override
    public void processRefund(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException("No refund requested.");
    }

    @Override
    public String getStateName() {
        return "PAID";
    }
}
