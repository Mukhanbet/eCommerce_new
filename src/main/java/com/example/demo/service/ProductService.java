package com.example.demo.service;

import com.example.demo.dto.product.ProductRequest;
import com.example.demo.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll();
    List<ProductResponse> getAllAvailableProducts();
    List<ProductResponse> getSolvedProducts();
    List<ProductResponse> getProductsSortedByPrice(String order);
    List<ProductResponse> getPopularProducts();
    List<ProductResponse> getByCategory(String category);
    List<ProductResponse> getByBrand(String brand);
    List<String> compareWith(Long firstId, Long secondId);
    List<ProductResponse> getSolvedProductsByCategory(String category);
    ProductResponse findById(Long id);
    void updateById(Long id, ProductRequest productRequest);
    void deleteById(Long id);
    void create(ProductRequest productRequest);
}
