package com.example.demo.service;

import com.example.demo.dto.product.ProductRequest;
import com.example.demo.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll();
    List<ProductResponse> getAllAvailableProducts();
    List<ProductResponse> getSolvedProducts();
    List<ProductResponse> getProductsSortedByPrice(String order);
    List<ProductResponse> getByType(String type);
    List<String> compareWith(Long firstId, Long secondId);
    List<ProductResponse> getSolvedProductsByType(String type);
    ProductResponse findById(Long id);
    void putTheProductToBasket(Long productId, String userEmail);
    void updateById(Long id, ProductRequest productRequest);
    void deleteById(Long id);
    void create(ProductRequest productRequest, String managerEmail);
}
