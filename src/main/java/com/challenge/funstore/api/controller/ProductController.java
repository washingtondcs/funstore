package com.challenge.funstore.api.controller;
import com.challenge.funstore.api.service.ProductService;
import com.challenge.funstore.domain.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return productService.updateProduct(id, product)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        Optional<Product> product = productService.deleteProduct(id);
        if (product.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Product successfully deleted");
            response.put("product", product.get());
            return ResponseEntity.ok().body(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Title not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        Optional<Product> product = productService.getProductById(id);
        if (!product.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Title not found"));
        }
        return ResponseEntity.ok(product.get());
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/title")
    public ResponseEntity<?> searchProductsByDescription(@RequestParam String description) {
        List<Product> products = productService.searchProductsByDescription(description);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "No titles found"));
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/type")
    public ResponseEntity<?> filterProductsByTypeNameOrType(@RequestParam(required = false) String productTypeName, @RequestParam(required = false) Integer productType) {
        List<Product> products = productService.filterProductsByTypeNameOrType(productTypeName, productType);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "No products found"));
        }
        return ResponseEntity.ok(products);
    }
}