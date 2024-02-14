package com.example.demo.mapper.impl;

import com.example.demo.dto.brand.BrandResponse;
import com.example.demo.entities.Brand;
import com.example.demo.mapper.BrandMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BrandMapperImpl implements BrandMapper {
    @Override
    public BrandResponse toDto(Brand brand) {
        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(brand.getId());
        brandResponse.setName(brand.getName());
        brandResponse.setPlaceOfProduction(brand.getPlaceOfProduction());
        return brandResponse;
    }

    @Override
    public List<BrandResponse> toDtoS(List<Brand> all) {
        List<BrandResponse> brandResponses = new ArrayList<>();
        for(Brand brand : all) {
            brandResponses.add(toDto(brand));
        }
        return brandResponses;
    }
}
