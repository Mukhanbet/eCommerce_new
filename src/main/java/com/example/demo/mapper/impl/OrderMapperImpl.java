package com.example.demo.mapper.impl;

import com.example.demo.dto.order.OrderResponse;
import com.example.demo.entities.Order;
import com.example.demo.mapper.OrderMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapperImpl implements OrderMapper {
    @Override
    public OrderResponse toDto(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setUserEmail(order.getEnrolUserToOrder().getEmail());
        orderResponse.setProductId(order.getEnrolProductToOrder().getId());
        orderResponse.setAmount(order.getAmount());
        orderResponse.setAddress(order.getAddress());
        orderResponse.setPayment(String.valueOf(order.getPayment()));
        orderResponse.setSum(order.getSum());
        orderResponse.setDateOfOrder(order.getDateOfOrder());
        orderResponse.setStatus(String.valueOf(order.getStatus()));
        return orderResponse;
    }

    @Override
    public List<OrderResponse> toDtoS(List<Order> all) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for(Order order : all) {
            orderResponses.add(toDto(order));
        }
        return orderResponses;
    }
}
