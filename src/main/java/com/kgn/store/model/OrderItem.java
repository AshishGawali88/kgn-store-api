package com.kgn.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItem {

    private Long productId;

    @Column(length = 200)
    private String name;

    private int qty;
    private int price;   // unit price at time of order

    public OrderItem() { }

    public OrderItem(Long productId, String name, int qty, int price) {
        this.productId = productId; this.name = name; this.qty = qty; this.price = price;
    }

    public Long getProductId() { return productId; }
    public String getName() { return name; }
    public int getQty() { return qty; }
    public int getPrice() { return price; }

    public void setProductId(Long productId) { this.productId = productId; }
    public void setName(String name) { this.name = name; }
    public void setQty(int qty) { this.qty = qty; }
    public void setPrice(int price) { this.price = price; }
}
