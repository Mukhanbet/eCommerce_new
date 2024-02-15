package com.example.demo.mapper.impl;

import com.example.demo.dto.product.ProductResponse;
import com.example.demo.entities.Product;
import com.example.demo.mapper.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductResponse toDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setAmount(product.getAmount());
        productResponse.setCategory(product.getCategory().getName());
        productResponse.setBrand(product.getBrand().getName());
        productResponse.setSize(product.getSize());
        productResponse.setColor(product.getColor());
        productResponse.setAvailable(product.isAvailable());
        return productResponse;
    }

    @Override
    public List<ProductResponse> toDtoS(List<Product> all) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product : all) {
            productResponses.add(toDto(product));
        }
        return productResponses;
    }
}
