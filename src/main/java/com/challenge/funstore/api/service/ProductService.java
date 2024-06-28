package com.challenge.funstore.api.service;

import com.challenge.funstore.domain.entity.Product;
import com.challenge.funstore.domain.enums.ProductEnums;
import com.challenge.funstore.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        setProductTypeAndAvailableForNames(product);
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Integer id, Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setDescription(productDetails.getDescription());
                    product.setAvailableFor(productDetails.getAvailableFor());
                    product.setProductType(productDetails.getProductType());
                    product.setSaleValue(productDetails.getSaleValue());
                    product.setRentalValue(productDetails.getRentalValue());
                    setProductTypeAndAvailableForNames(product);
                    return productRepository.save(product);
                });
    }

    public Optional<Product> deleteProduct(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product p = product.get();
            p.setDeleted(true);
            productRepository.save(p);
            return Optional.of(p);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id)
                .filter(product -> !product.isDeleted());
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream()
                .filter(product -> !product.isDeleted())
                .collect(Collectors.toList());
    }

    public List<Product> searchProductsByDescription(String description) {
        return productRepository.findByDescriptionContainingIgnoreCase(description);
    }

    public List<Product> filterProductsByTypeNameOrType(String productTypeName, Integer productType) {
        return productRepository.findByProductTypeNameIgnoreCaseOrProductType(productTypeName, productType);
    }

    private void setProductTypeAndAvailableForNames(Product product) {
        // Definindo o nome do tipo de produto com base no ENUM ProductType
        if (product.getProductType() != null) {
            ProductEnums.ProductType productType = ProductEnums.ProductType.findByCode(Integer.valueOf(product.getProductType()));
            product.setProductTypeName(productType.getDisplayName());
        }

        // Definindo o nome do status de disponibilidade com base no ENUM AvailableFor
        if (product.getAvailableFor() != null) {
            ProductEnums.AvailableFor availableFor = ProductEnums.AvailableFor.findByCode(Integer.valueOf(product.getAvailableFor()));
            product.setAvailableForName(availableFor.getDescription());
        }
    }
}