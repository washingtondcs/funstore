package com.challenge.funstore.api.controller;

import com.challenge.funstore.api.service.CustomerService;
import com.challenge.funstore.domain.dto.CustomerDTO;
import com.challenge.funstore.domain.entity.Customer;
import com.challenge.funstore.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        if (customerService.existsByCpf(customer.getCpf())) {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body(Collections.singletonMap("error", "A customer with the same CPF already exists."));
        }

        Customer savedCustomer = customerService.saveCustomer(customer);
        CustomerDTO customerDTO = DTOConverter.customerToDTO(savedCustomer);

        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (!customer.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Customer not found"));
        }
        return ResponseEntity.ok(customer.get());
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @GetMapping("/search")
    public ResponseEntity<?> filterCustomersByNameOrCpf(@RequestParam(required = false) String name, @RequestParam(required = false) String cpf) {
        List<Customer> customers = customerService.filterCustomersByNameOrCpf(name, cpf);
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "No customers found"));
        }
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(DTOConverter::customerToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody Customer customerDetails) {
        // Verifica se o CPF já existe para outro cliente
        if (customerService.cpfExistsForOtherCustomer(id, customerDetails.getCpf())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "CPF already exists for another customer."));
        }
        return customerService.updateCustomer(id, customerDetails)
                .map(customer -> ResponseEntity.ok(DTOConverter.customerToDTO(customer)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        Optional<Customer> customer = customerService.deleteCustomer(id);
        if (customer.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Customer successfully deleted");
            response.put("customer", customer.get());
            return ResponseEntity.ok().body(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Customer not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}