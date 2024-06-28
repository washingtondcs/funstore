package com.challenge.funstore.domain.repository;

import com.challenge.funstore.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByDescriptionContainingIgnoreCase(String name);
    List<Product> findByProductTypeNameIgnoreCaseOrProductType(String productTypeName, Integer productType);
}
