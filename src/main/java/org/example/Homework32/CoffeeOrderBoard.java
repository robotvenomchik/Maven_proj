package org.example.Homework32;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class CoffeeOrderBoard {
    private static final Logger logger = LogManager.getLogger(CoffeeOrderBoard.class);
    private final List<Order> orders = new LinkedList<>();
    private int nextOrderNumber = 1;

    public void add(String name) {
        long startTime = System.nanoTime();
        try {
            Order order = new Order(nextOrderNumber++, name);
            orders.add(order);
            logger.info("Added new order: {}", order);
            logger.debug("Queue size after adding: {}", orders.size());
        } catch (Exception e) {
            logger.error("Failed to add order for name '{}'", name, e);
        } finally {
            long endTime = System.nanoTime();
            logger.debug("Execution time for add(): {} ms", (endTime - startTime) / 1_000_000);
        }
    }

    public Order deliver() {
        long startTime = System.nanoTime();
        try {
            if (orders.isEmpty()) {
                logger.warn("No orders to deliver!");
                return null;
            }
            Order nextOrder = orders.remove(0);
            logger.info("Delivered order: {}", nextOrder);
            logger.debug("Queue size after delivery: {}", orders.size());
            return nextOrder;
        } catch (Exception e) {
            logger.error("Failed to deliver order", e);
            return null;
        } finally {
            long endTime = System.nanoTime();
            logger.debug("Execution time for deliver(): {} ms", (endTime - startTime) / 1_000_000);
        }
    }

    public Order deliver(int orderNumber) {
        long startTime = System.nanoTime();
        try {
            for (Order order : orders) {
                if (order.getNumber() == orderNumber) {
                    orders.remove(order);
                    logger.info("Delivered specific order: {}", order);
                    logger.debug("Queue size after delivery: {}", orders.size());
                    return order;
                }
            }
            logger.warn("Order with number {} not found!", orderNumber);
            return null;
        } catch (Exception e) {
            logger.error("Failed to deliver specific order with number {}", orderNumber, e);
            return null;
        } finally {
            long endTime = System.nanoTime();
            logger.debug("Execution time for deliver(int): {} ms", (endTime - startTime) / 1_000_000);
        }
    }

    public void draw() {
        try {
            logger.info("Drawing current orders in queue:");
            System.out.println("Num | Name");
            for (Order order : orders) {
                System.out.println(order);
            }
        } catch (Exception e) {
            logger.error("Failed to draw queue", e);
        }
    }
}
