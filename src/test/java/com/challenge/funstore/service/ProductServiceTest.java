package com.challenge.funstore.service;

import com.challenge.funstore.api.service.ProductService;
import com.challenge.funstore.domain.entity.Product;
import com.challenge.funstore.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenSaveProduct_thenSuccess() {
        Product product = new Product();
        product.setDescription("The Matrix Reloaded");
        product.setProductType(2);
        product.setSaleValue(new BigDecimal("19.99"));

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.createProduct(product);

        assertNotNull(savedProduct);
        assertEquals("The Matrix Reloaded", savedProduct.getDescription());
    }
}