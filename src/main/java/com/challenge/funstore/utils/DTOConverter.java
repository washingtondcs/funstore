package com.challenge.funstore.utils;

import com.challenge.funstore.domain.dto.CustomerDTO;
import com.challenge.funstore.domain.entity.Customer;

public class DTOConverter {

    public static CustomerDTO customerToDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCpf(),
                customer.getDateCreated(),
                customer.getDateUpdated());
    }
}