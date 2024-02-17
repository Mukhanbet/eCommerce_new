package com.example.demo.service;

import com.example.demo.dto.basket.BasketRequest;
import com.example.demo.dto.basket.BasketResponse;

import java.util.List;

public interface BasketService {
    List<BasketResponse> getAll();
    List<BasketResponse> getByStatus(String status);
    BasketResponse findById(Long id);
    void updateById(Long id, BasketRequest basketRequest);
    void deleteById(Long id);
    void putProductToBasket(Long productId, String userEmail, BasketRequest basketRequest);
    void systemUpdates();
}
