package com.java.design.patterns.behavioral.state.state.implementation;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.exception.InvalidStateTransitionException;
import com.java.design.patterns.behavioral.state.model.OrderStatus;
import com.java.design.patterns.behavioral.state.model.ShippingDetails;
import com.java.design.patterns.behavioral.state.state.OrderState;

import java.time.LocalDateTime;

public class PreparingState implements OrderState {

    @Override
    public void prepareOrder(OrderContext context) {
        // Already preparing, idempotent behavior
        System.out.println("Order " + context.getOrder().getOrderId() + " is already in PREPARING state.");
    }

    @Override
    public void shipOrder(OrderContext context, String trackingNumber) {
        System.out.println("Shipping order: " + context.getOrder().getOrderId());

        // Populate / update shipping details
        ShippingDetails shippingDetails = context.getOrder().getShippingDetails();
        if (shippingDetails == null) {
            shippingDetails = new ShippingDetails();
            context.getOrder().setShippingDetails(shippingDetails);
        }

        shippingDetails.setTrackingNumber(trackingNumber);
        shippingDetails.setShippedAt(LocalDateTime.now());
        // For demo, set ETA as +3 days
        shippingDetails.setEstimatedDeliveryDate(LocalDateTime.now().plusDays(3));

        // Call ShippingService to schedule shipment
        context.getShippingService().scheduleShipment(context.getOrder(), trackingNumber);

        // Transition to SHIPPED state
        context.setState(new ShippedState());
        context.setStatus(OrderStatus.SHIPPED);

        // Notify customer
        context.getNotificationService().sendEmail(
                context.getOrder().getCustomerId(),
                "Order Shipped",
                "Your order " + context.getOrder().getOrderId()
                        + " has been shipped. Tracking: " + trackingNumber
        );
    }

    @Override
    public void processPayment(OrderContext context) {
        throw new InvalidStateTransitionException(
                "Payment already processed. Order is in PREPARING state.");
    }

    @Override
    public void confirmPayment(OrderContext context) {
        throw new InvalidStateTransitionException(
                "Payment already confirmed. Order is in PREPARING state.");
    }

    @Override
    public void deliverOrder(OrderContext context) {
        throw new InvalidStateTransitionException(
                "Cannot deliver order directly from PREPARING state. Ship the order first.");
    }

    @Override
    public void cancelOrder(OrderContext context, String reason) {
        // Business decision: allow or disallow cancellation in PREPARING
        System.out.println("Cancelling order while preparing: " + context.getOrder().getOrderId());

        // Release inventory
        context.getInventoryService().releaseItems(context.getOrder().getItems());

        context.getOrder().setCancellationReason(reason);
        context.setState(new CancelledState());
        context.setStatus(OrderStatus.CANCELLED);

        context.getNotificationService().sendEmail(
                context.getOrder().getCustomerId(),
                "Order Cancelled",
                "Your order was cancelled while it was being prepared. Reason: " + reason
        );
    }

    @Override
    public void requestRefund(OrderContext context, String reason) {
        throw new InvalidStateTransitionException(
                "Use cancelOrder to cancel during PREPARING; refunds are handled as part of cancellation.");
    }

    @Override
    public void processRefund(OrderContext context) {
        throw new InvalidStateTransitionException(
                "No refund requested for order in PREPARING state.");
    }

    @Override
    public String getStateName() {
        return "PREPARING";
    }
}
