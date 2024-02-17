package com.example.demo.mapper.impl;

import com.example.demo.dto.basket.BasketResponse;
import com.example.demo.entities.Basket;
import com.example.demo.mapper.BasketMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BasketMapperImpl implements BasketMapper {
    @Override
    public BasketResponse toDto(Basket basket) {
        BasketResponse basketResponse = new BasketResponse();
        basketResponse.setId(basket.getId());
        basketResponse.setUserEmail(basket.getEnrolUser().getEmail());
        basketResponse.setProduct(basket.getEnrolProduct().getName()); // todo what should display
        basketResponse.setTotalSum(basket.getTotalSum());
        basketResponse.setCreatedDay(basket.getCreatedDay());
        basketResponse.setEndDay(basket.getEndDay());
        basketResponse.setStatus(String.valueOf(basket.getStatus()));
        basketResponse.setDelivery(basket.getDelivery());
        basketResponse.setPayment(basket.getPayment());
        basketResponse.setAmount(basket.getAmount());
        return basketResponse;
    }

    @Override
    public List<BasketResponse> toDtoS(List<Basket> all) {
        List<BasketResponse> basketResponses = new ArrayList<>();
        for(Basket basket : all) {
            basketResponses.add(toDto(basket));
        }
        return basketResponses;
    }
}
