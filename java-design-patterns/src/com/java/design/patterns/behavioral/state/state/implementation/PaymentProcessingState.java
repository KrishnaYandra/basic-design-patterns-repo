package com.java.design.patterns.behavioral.state.state.implementation;

import com.java.design.patterns.behavioral.state.context.OrderContext;
import com.java.design.patterns.behavioral.state.exception.InvalidStateTransitionException;
import com.java.design.patterns.behavioral.state.model.OrderStatus;
import com.java.design.patterns.behavioral.state.state.OrderState;

public class PaymentProcessingState implements OrderState {

    @Override
    public void confirmPayment(OrderContext context) throws InvalidStateTransitionException {
        System.out.println("Confirming payment for order: " + context.getOrder().getOrderId());
        
        // Process payment through payment gateway
        boolean paymentSuccess = context.getPaymentService()
            .processPayment(context.getOrder().getPaymentDetails());
        
        if (paymentSuccess) {
            // Reserve inventory
            boolean inventoryReserved = context.getInventoryService()
                .reserveItems(context.getOrder().getItems());
            
            if (inventoryReserved) {
                context.setState(new PaidState());
                context.setStatus(OrderStatus.PAID);
                
                context.getNotificationService().sendEmail(
                    context.getOrder().getCustomerId(),
                    "Payment Confirmed",
                    "Your payment has been confirmed. Order is being prepared."
                );
            } else {
                // Refund if inventory not available
                context.getPaymentService().refundPayment(
                    context.getOrder().getPaymentDetails());
                context.setState(new CancelledState());
                context.setStatus(OrderStatus.CANCELLED);
                
                context.getNotificationService().sendEmail(
                    context.getOrder().getCustomerId(),
                    "Order Cancelled - Out of Stock",
                    "Your order was cancelled due to insufficient inventory. Payment refunded."
                );
            }
        } else {
            context.setState(new PendingPaymentState());
            context.setStatus(OrderStatus.PENDING_PAYMENT);
            
            context.getNotificationService().sendEmail(
                context.getOrder().getCustomerId(),
                "Payment Failed",
                "Your payment failed. Please try again."
            );
        }
    }

    @Override
    public void cancelOrder(OrderContext context, String reason) {
        System.out.println("Cancelling order during payment processing: " + 
                         context.getOrder().getOrderId());
        context.getOrder().setCancellationReason(reason);
        context.setState(new CancelledState());
        context.setStatus(OrderStatus.CANCELLED);
    }

    @Override
    public void processPayment(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Payment is already being processed.");
    }

    @Override
    public void prepareOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot prepare order while payment is processing.");
    }

    @Override
    public void shipOrder(OrderContext context, String trackingNumber) 
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot ship order while payment is processing.");
    }

    @Override
    public void deliverOrder(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot deliver order while payment is processing.");
    }

    @Override
    public void requestRefund(OrderContext context, String reason) 
            throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot request refund while payment is processing.");
    }

    @Override
    public void processRefund(OrderContext context) throws InvalidStateTransitionException {
        throw new InvalidStateTransitionException(
            "Cannot process refund while payment is processing.");
    }

    @Override
    public String getStateName() {
        return "PAYMENT_PROCESSING";
    }
}
