package com.challenge.funstore.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {
    private Integer customer;
    private Integer type;
    private List<Integer> orderItems;
    private Integer rentalDays;
}
