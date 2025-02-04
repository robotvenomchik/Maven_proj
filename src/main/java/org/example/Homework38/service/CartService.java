package org.example.Homework38.service;


import org.example.Homework38.model.Cart;
import org.example.Homework38.model.Product;
import org.example.Homework38.repository.ProductRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Scope("prototype")
public class CartService {
    private final Cart cart;
    private final ProductRepository productRepository;

    public CartService(Cart cart, ProductRepository productRepository) {
        this.cart = cart;
        this.productRepository = productRepository;
    }

    public boolean addProductById(int productId) {
        return productRepository.findById(productId)
                .map(p -> { cart.addProduct(p); return true; })
                .orElse(false);
    }

    public boolean removeProductById(int productId) {
        if (cart.getItems().stream().anyMatch(p -> p.getId() == productId)) {
            cart.removeProduct(productId);
            return true;
        }
        return false;
    }

    public List<Product> viewCart() {
        return cart.getItems();
    }
    public double getTotalPrice() {
        return cart.getItems().stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

}