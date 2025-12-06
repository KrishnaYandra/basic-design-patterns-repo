package com.java.design.patterns.behavioral.state.model;

import java.math.BigDecimal;

public class OrderItem {
    private String productId;
    private String name;
    private int quantity;
    private BigDecimal price;

    public OrderItem(String productId, String name, int quantity, BigDecimal price) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() { return productId; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
}
