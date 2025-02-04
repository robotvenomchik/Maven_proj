package org.example.Homework37;

import java.sql.Timestamp;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

public class Order {
    private int id;
    private Date date;
    private BigDecimal cost; // Використовуємо BigDecimal
    private List<Product> products;

    public Order(int id, Timestamp date, BigDecimal cost, List<ProductDAO> orderProducts) {}

    public Order(int id, Date date, BigDecimal cost, List<Product> products) {
        this.id = id;
        this.date = date;
        this.cost = cost;
        this.products = products;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}
