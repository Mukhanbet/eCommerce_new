package com.example.demo.mapper;

import com.example.demo.dto.brand.BrandResponse;
import com.example.demo.entities.Brand;

import java.util.List;

public interface BrandMapper {
    BrandResponse toDto(Brand brand);
    List<BrandResponse> toDtoS(List<Brand> all);
}
