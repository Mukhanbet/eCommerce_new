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
        productResponse.setType(product.getType().getName());
        productResponse.setColor(product.getColor());
        productResponse.setYear(product.getYear());
        productResponse.setCountry(product.getCountry());
        productResponse.setPrice(product.getPrice());
        productResponse.setOwner(product.getManager().getName());
        productResponse.setAvailable(product.isAvailable());
        productResponse.setAmount(product.getAmount());
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
