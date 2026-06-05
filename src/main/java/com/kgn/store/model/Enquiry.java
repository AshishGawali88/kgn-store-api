package com.kgn.store.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "enquiries")
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String mobile;

    @Column(length = 120)
    private String email;

    @Column(length = 120)
    private String category;

    @Column(length = 2000)
    private String message;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getMobile() { return mobile; }
    public String getEmail() { return email; }
    public String getCategory() { return category; }
    public String getMessage() { return message; }
    public Instant getCreatedAt() { return createdAt; }

    public void setName(String name) { this.name = name; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setEmail(String email) { this.email = email; }
    public void setCategory(String category) { this.category = category; }
    public void setMessage(String message) { this.message = message; }
}
