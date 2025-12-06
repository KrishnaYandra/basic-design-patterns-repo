package com.java.design.patterns.behavioral.state.model;

import java.math.BigDecimal;

public class PaymentDetails {
    private String paymentMethod;
    private String cardLast4;
    private BigDecimal amount;

    public PaymentDetails(String paymentMethod, String cardLast4, BigDecimal amount) {
        this.paymentMethod = paymentMethod;
        this.cardLast4 = cardLast4;
        this.amount = amount;
    }

    public String getPaymentMethod() { return paymentMethod; }
    public String getCardLast4() { return cardLast4; }
    public BigDecimal getAmount() { return amount; }

    @Override
    public String toString() {
        return "PaymentDetails{" +
                "method='" + paymentMethod + '\'' +
                ", cardLast4='" + cardLast4 + '\'' +
                ", amount=" + amount +
                '}';
    }
}
