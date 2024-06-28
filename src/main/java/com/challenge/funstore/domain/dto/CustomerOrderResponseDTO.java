package com.challenge.funstore.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderResponseDTO {
    private Integer orderId;
    private String orderTypeName;
    private String customerName;
    private String customerCpf;
    private String customerEmail;
    private BigDecimal totalValue;
    private LocalDateTime dateCreated;
    private List<ProductDTO> itens;
}
