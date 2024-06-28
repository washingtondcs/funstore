package com.challenge.funstore.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 150)
    private String description;

    @Column(name = "available_for", length = 2)
    private String availableFor;

    @Column(name = "available_for_name", length = 30)
    private String availableForName;

    @Column(name = "product_type", length = 2)
    private Integer productType;

    @Column(name = "product_type_name", length = 30)
    private String productTypeName;

    @Column(name = "sale_value", precision = 5, scale = 2)
    private BigDecimal saleValue;

    @Column(name = "rentalValue", precision = 5, scale = 2)
    private BigDecimal rentalValue;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }
}
