package com.kgn.store.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    private Long id;                 // matches the ids used by the frontend

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 120)
    private String brand;

    @Column(nullable = false, length = 40)
    private String cat;              // category key: sealers, cleaners, adhesives, ...

    @Column(length = 300)
    private String img;

    private int price;
    private int mrp;
    private double rating;
    private int reviews;
    private int stock;

    @Column(length = 20)
    private String badge;

    @Column(length = 500)
    private String spec;

    public Product() { }

    public Product(Long id, String name, String brand, String cat, String img,
                   int price, int mrp, double rating, int reviews, int stock,
                   String badge, String spec) {
        this.id = id; this.name = name; this.brand = brand; this.cat = cat;
        this.img = img; this.price = price; this.mrp = mrp; this.rating = rating;
        this.reviews = reviews; this.stock = stock; this.badge = badge; this.spec = spec;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getCat() { return cat; }
    public String getImg() { return img; }
    public int getPrice() { return price; }
    public int getMrp() { return mrp; }
    public double getRating() { return rating; }
    public int getReviews() { return reviews; }
    public int getStock() { return stock; }
    public String getBadge() { return badge; }
    public String getSpec() { return spec; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setCat(String cat) { this.cat = cat; }
    public void setImg(String img) { this.img = img; }
    public void setPrice(int price) { this.price = price; }
    public void setMrp(int mrp) { this.mrp = mrp; }
    public void setRating(double rating) { this.rating = rating; }
    public void setReviews(int reviews) { this.reviews = reviews; }
    public void setStock(int stock) { this.stock = stock; }
    public void setBadge(String badge) { this.badge = badge; }
    public void setSpec(String spec) { this.spec = spec; }
}
