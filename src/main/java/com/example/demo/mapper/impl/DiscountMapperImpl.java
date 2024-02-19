package com.example.demo.mapper.impl;

import com.example.demo.dto.discount.DiscountResponse;
import com.example.demo.entities.Discount;
import com.example.demo.mapper.DiscountMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiscountMapperImpl implements DiscountMapper {
    @Override
    public DiscountResponse toDto(Discount discount) {
        DiscountResponse discountResponse = new DiscountResponse();
        discountResponse.setId(discount.getId());
        discountResponse.setName(discount.getName());
        discountResponse.setDiscount(discount.getDiscount());
        discountResponse.setStartDate(discount.getStartDate());
        discountResponse.setEndDate(discount.getEndDate());
        discountResponse.setStatus(String.valueOf(discount.getStatus()));
        return discountResponse;
    }

    @Override
    public List<DiscountResponse> toDtoS(List<Discount> all) {
        List<DiscountResponse> discountResponses = new ArrayList<>();
        for(Discount discount : all) {
            discountResponses.add(toDto(discount));
        }
        return discountResponses;
    }
}
