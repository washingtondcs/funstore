package com.challenge.funstore.domain.dto;

import com.challenge.funstore.domain.entity.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Integer orderId;
    private BigDecimal totalValue;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private String name;
    private String cpf;
    private List<OrderItems> items;
}