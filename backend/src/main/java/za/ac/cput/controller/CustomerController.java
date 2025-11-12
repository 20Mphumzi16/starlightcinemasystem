package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Customer;
import za.ac.cput.service.CustomerService.CustomerService;

import java.util.List;
@CrossOrigin("localhost:3000")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public Customer create(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @GetMapping("/read/{id}")
    public Customer read(@PathVariable Long id) {
        return customerService.read(id);
    }

    @PostMapping("/update")
    public Customer update(@RequestBody Customer customer) {
        return customerService.update(customer);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return customerService.delete(id);
    }

    @GetMapping("/login/{username}/{password}")
    public Customer login(@PathVariable String username,@PathVariable String password) {
        return customerService.getByEmailAndPassword(username, password);
    }

    @GetMapping("/get-all")
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

}
