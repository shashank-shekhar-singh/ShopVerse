package com.shopverse.productservice.controller;

import com.shopverse.productservice.dto.ProductRequest;
import com.shopverse.productservice.dto.ProductResponse;
import com.shopverse.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest requestDTO) {
        return ResponseEntity.ok(productService.createProduct(requestDTO));
    }

    @PutMapping
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid ProductRequest requestDTO) {
        return ResponseEntity.ok(productService.updateProduct(requestDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestParam Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
