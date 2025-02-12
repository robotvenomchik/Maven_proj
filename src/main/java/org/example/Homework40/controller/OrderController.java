package org.example.Homework40.controller;


import org.example.Homework40.model.Order;
import org.example.Homework40.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable String id) {
        return orderRepository.getOrderById(id);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @PostMapping
    public void addOrder(@RequestBody Order order) {
        orderRepository.addOrder(order);
    }
}

