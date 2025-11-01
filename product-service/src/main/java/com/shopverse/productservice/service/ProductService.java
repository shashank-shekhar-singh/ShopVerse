package com.shopverse.productservice.service;

import com.shopverse.productservice.dto.ProductPurchaseRequest;
import com.shopverse.productservice.dto.ProductPurchaseResponse;
import com.shopverse.productservice.dto.ProductRequest;
import com.shopverse.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    Integer createProduct(ProductRequest request);
    ProductResponse findById(Integer id);
    List<ProductResponse> findAll();
    List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request);
}
