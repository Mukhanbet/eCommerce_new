package com.example.demo.mapper;

import com.example.demo.dto.discount.DiscountResponse;
import com.example.demo.entities.Discount;

import java.util.List;

public interface DiscountMapper {
    DiscountResponse toDto(Discount discount);
    List<DiscountResponse> toDtoS(List<Discount> all);
}
