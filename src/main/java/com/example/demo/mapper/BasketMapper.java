package com.example.demo.mapper;

import com.example.demo.dto.basket.BasketResponse;
import com.example.demo.entities.Basket;

import java.util.List;

public interface BasketMapper {
    BasketResponse toDto(Basket basket);
    List<BasketResponse> toDtoS(List<Basket> all);
}
