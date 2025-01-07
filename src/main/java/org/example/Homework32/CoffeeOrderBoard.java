package org.example.Homework32;
import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CoffeeOrderBoard {
    private static final Logger logger = LogManager.getLogger(CoffeeOrderBoard.class);
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public CoffeeOrderBoard() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hillel-persistence-unit");
        entityManager = entityManagerFactory.createEntityManager();
        logger.info("EntityManager initialized.");
    }

    public void add(String name) {
        Order order = new Order(name);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(order);
            entityManager.getTransaction().commit();
            logger.info("Added new order for: {}", name);
        } catch (Exception e) {
            logger.error("Failed to add order for: {}", name, e);
            entityManager.getTransaction().rollback();
        }
    }

    public void deliver() {
        try {
            entityManager.getTransaction().begin();
            Order order = entityManager.createQuery(
                            "SELECT o FROM Order o WHERE o.status = 'Pending' ORDER BY o.id", Order.class)
                    .setMaxResults(1)
                    .getSingleResult();

            order.setStatus("Delivered");
            entityManager.merge(order);
            entityManager.getTransaction().commit();
            logger.info("Delivered order: {} | {}", order.getId(), order.getName());
        } catch (NoResultException e) {
            logger.warn("No orders to deliver!");
            entityManager.getTransaction().rollback();
        } catch (Exception e) {
            logger.error("Failed to deliver order", e);
            entityManager.getTransaction().rollback();
        }
    }

    public void deliver(int orderNumber) {
        try {
            entityManager.getTransaction().begin();
            Order order = entityManager.find(Order.class, orderNumber);
            if (order != null && "Pending".equals(order.getStatus())) {
                order.setStatus("Delivered");
                entityManager.merge(order);
                entityManager.getTransaction().commit();
                logger.info("Delivered specific order: {} | {}", order.getId(), order.getName());
            } else {
                logger.warn("Order with number {} not found or already delivered!", orderNumber);
                entityManager.getTransaction().rollback();
            }
        } catch (Exception e) {
            logger.error("Failed to deliver specific order with number {}", orderNumber, e);
            entityManager.getTransaction().rollback();
        }
    }

    public void draw() {
        try {
            List<Order> orders = entityManager.createQuery(
                            "SELECT o FROM Order o WHERE o.status = 'Pending' ORDER BY o.id", Order.class)
                    .getResultList();
            System.out.println("Num | Name");
            for (Order order : orders) {
                System.out.println(order.getId() + " | " + order.getName());
            }
            logger.info("Displayed current queue.");
        } catch (Exception e) {
            logger.error("Failed to display queue", e);
        }
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
        logger.info("EntityManager closed.");
    }
}
