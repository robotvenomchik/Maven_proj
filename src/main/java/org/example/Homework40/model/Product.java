package org.example.Homework40.model;
import java.util.UUID;

public class Product {
    private String id;
    private String name;
    private double cost;

    public Product(String name, double cost) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.cost = cost;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getCost() { return cost; }
}