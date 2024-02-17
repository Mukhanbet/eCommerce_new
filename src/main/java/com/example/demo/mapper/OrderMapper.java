package com.example.demo.mapper;

import com.example.demo.dto.order.OrderResponse;
import com.example.demo.entities.Order;

import java.util.List;

public interface OrderMapper {
    OrderResponse toDto(Order order);
    List<OrderResponse> toDtoS(List<Order> all);
}
