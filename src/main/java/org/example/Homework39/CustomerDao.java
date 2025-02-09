package org.example.Homework39;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDao {
    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (full_name, email, social_security_number) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getEmail(), customer.getSocialSecurityNumber());
    }

    public Optional<Customer> findById(Long id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        return jdbcTemplate.query(sql, new CustomerRowMapper(), id)
                .stream()
                .findFirst();
    }

    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, new CustomerRowMapper());
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET full_name = ?, email = ?, social_security_number = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getEmail(), customer.getSocialSecurityNumber(), customer.getId());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
