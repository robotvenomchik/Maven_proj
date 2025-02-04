package org.example.Homework37;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public void addOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (date, cost) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, new Timestamp(order.getDate().getTime()));
            stmt.setBigDecimal(2, order.getCost());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                }
            }

            addOrderProducts(order.getId(), order.getProducts(), conn);
        }
    }

    private void addOrderProducts(int orderId, List<Product> products, Connection conn) throws SQLException {
        String sql = "INSERT INTO order_products (order_id, product_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Product product : products) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, product.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }


    public Order getOrderById(int id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Order(rs.getInt("id"), rs.getTimestamp("date"), rs.getBigDecimal("cost"), getOrderProducts(id, conn));
                }
            }
        }
        return null;
    }

    private List<Product> getOrderProducts(int orderId, Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.* FROM product p JOIN order_products op ON p.id = op.product_id WHERE op.order_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getBigDecimal("cost")));
                }
            }
        }
        return products;
    }


    public void updateOrder(Order order) throws SQLException {
        String sql = "UPDATE orders SET date = ?, cost = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, new Timestamp(order.getDate().getTime()));
            stmt.setBigDecimal(2, order.getCost());
            stmt.setInt(3, order.getId());
            stmt.executeUpdate();

            deleteOrderProducts(order.getId(), conn);
            addOrderProducts(order.getId(), order.getProducts(), conn);
        }
    }

    private void deleteOrderProducts(int orderId, Connection conn) throws SQLException {
        String sql = "DELETE FROM order_products WHERE order_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        }
    }

    public void deleteOrder(int id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
