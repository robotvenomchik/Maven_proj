package org.example.Homework38.model;


import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Cart {
    private final List<Product> items = new ArrayList<>();

    public void addProduct(Product product) {
        items.add(product);
    }

    public void removeProduct(int productId) {
        items.removeIf(p -> p.getId() == productId);
    }

    public List<Product> getItems() {
        return new ArrayList<>(items);
    }

    public void clear() {
        items.clear();
    }
}
