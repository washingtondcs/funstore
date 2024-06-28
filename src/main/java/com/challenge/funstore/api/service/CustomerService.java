package com.challenge.funstore.api.service;

import com.challenge.funstore.domain.entity.Customer;
import com.challenge.funstore.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .filter(product -> !product.isDeleted());
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> filterCustomersByNameOrCpf(String name, String cpf) {
        return customerRepository.findByNameContainingIgnoreCaseOrCpf(name, cpf);
    }

    public Optional<Customer> updateCustomer(Integer id, Customer customerDetails) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(customerDetails.getName());
                    customer.setCpf(customerDetails.getCpf());
                    return customerRepository.save(customer);
                });
    }

    public boolean existsByCpf(String cpf) {
        return customerRepository.existsByCpf(cpf);
    }

    public Optional<Customer> deleteCustomer(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer c = customer.get();
            c.setDeleted(true);
            customerRepository.save(c);
            return Optional.of(c);
        } else {
            return Optional.empty();
        }
    }
    public boolean cpfExistsForOtherCustomer(Integer customerId, String cpf) {
        return customerRepository.existsByCpfAndIdNot(cpf, customerId);
    }
}
