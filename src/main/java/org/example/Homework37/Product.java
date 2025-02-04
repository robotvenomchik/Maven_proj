package org.example.Homework37;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private BigDecimal cost;

    public Product() {}

    public Product(int id, String name, BigDecimal cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
}
