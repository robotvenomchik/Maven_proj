package org.example.Homework40.repository;

import org.example.Homework40.model.Order;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();

    public Order getOrderById(String id) { return orders.get(id); }
    public List<Order> getAllOrders() { return new ArrayList<>(orders.values()); }
    public void addOrder(Order order) { orders.put(order.getId(), order); }
}
