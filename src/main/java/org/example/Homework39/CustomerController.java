package org.example.Homework39;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerDao customerDao;

    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @PostMapping
    public void addCustomer(@RequestBody Customer customer) {
        customerDao.addCustomer(customer);
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Long id) {
        return customerDao.findById(id);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id);
        customerDao.updateCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerDao.deleteById(id);
    }
}
