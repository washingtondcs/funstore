package com.challenge.funstore.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String description;
    private Integer productType;
    private String productTypeName;
    private BigDecimal rentalValue;
    private BigDecimal saleValue;
}
