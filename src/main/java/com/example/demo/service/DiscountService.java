package com.example.demo.service;

import com.example.demo.dto.discount.DiscountRequest;
import com.example.demo.dto.discount.DiscountResponse;

import java.util.List;
import java.util.Map;

public interface DiscountService {
    List<DiscountResponse> getAll();
    DiscountResponse findById(Long id);
    void updateById(Long id, DiscountRequest discountRequest);
    void updateByFields(Long id, Map<String, Object> fields);
    void deleteById(Long id);
    void create(DiscountRequest discountRequest);
}
