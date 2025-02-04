package org.example.Homework38.repository;

import org.example.Homework38.model.Product;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    @PostConstruct
    public void init() {
        products.add(new Product(1, "PS4", 1299.9));
        products.add(new Product(2, "Asus rog strix g17", 999.99));
        products.add(new Product(3, "Iphone 18 XSS ULTRA J12 HS2", 1599.9));
    }

    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    public Optional<Product> findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst();
    }
}