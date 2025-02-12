package org.example.Homework40.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

public class Order {
    private String id;
    private LocalDateTime creationDate;
    private double totalCost;
    private List<Product> products;

    @JsonCreator
    public Order(@JsonProperty("products") List<Product> products) {
        this.id = UUID.randomUUID().toString();
        this.creationDate = LocalDateTime.now();
        this.products = products;
        this.totalCost = products.stream().mapToDouble(Product::getCost).sum();
    }

    public Order() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        this.totalCost = products.stream().mapToDouble(Product::getCost).sum();
    }
}
