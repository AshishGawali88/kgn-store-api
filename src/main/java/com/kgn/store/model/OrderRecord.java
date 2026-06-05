package com.kgn.store.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String orderCode;     // e.g. KGN-4821

    @Column(nullable = false, length = 100)
    private String customerName;

    @Column(nullable = false, length = 20)
    private String mobile;

    @Column(length = 600)
    private String address;

    @Column(length = 40)
    private String paymentMethod;

    private int total;

    @Column(nullable = false, length = 20)
    private String status = "NEW";

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public Long getId() { return id; }
    public String getOrderCode() { return orderCode; }
    public String getCustomerName() { return customerName; }
    public String getMobile() { return mobile; }
    public String getAddress() { return address; }
    public String getPaymentMethod() { return paymentMethod; }
    public int getTotal() { return total; }
    public String getStatus() { return status; }
    public List<OrderItem> getItems() { return items; }
    public Instant getCreatedAt() { return createdAt; }

    public void setOrderCode(String orderCode) { this.orderCode = orderCode; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setAddress(String address) { this.address = address; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setTotal(int total) { this.total = total; }
    public void setStatus(String status) { this.status = status; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
