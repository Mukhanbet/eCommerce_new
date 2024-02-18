package com.example.demo.service;

import com.example.demo.dto.order.OrderRequest;
import com.example.demo.dto.order.OrderResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderService {
    List<OrderResponse> getAll();
    List<OrderResponse> getUserOrders(String userEmail);
    List<OrderResponse> getOrdersByStatus(String status);
    List<OrderResponse> getOrdersByPayment(String payment);
    List<OrderResponse> getOrdersByDate(LocalDate date);
    List<OrderResponse> getOrdersByProduct(Long productId);
    OrderResponse findById(Long id);
    void updateById(OrderRequest orderRequest, Long id);
    void updateByField(Long id, Map<String, Object> fields);
    void deleteById(Long id);
    void orderOperation(String userEmail, Long productId, OrderRequest orderRequest);
}
