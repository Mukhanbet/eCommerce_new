package com.example.demo.service;

import com.example.demo.dto.brand.BrandRequest;
import com.example.demo.dto.brand.BrandResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAll();
    BrandResponse findByName(String name);
    void updateByName(String name, BrandRequest brandRequest);
    void deleteByName(String name);
    void create(BrandRequest brandRequest);
}
