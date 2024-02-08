package com.example.demo.mapper.impl;

import com.example.demo.dto.seller.SellerResponse;
import com.example.demo.entities.Seller;
import com.example.demo.mapper.SellerMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SellerMapperImpl implements SellerMapper {
    @Override
    public SellerResponse toDto(Seller seller) {
        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.setId(seller.getId());
        sellerResponse.setEmail(seller.getEmail());
        sellerResponse.setRole(String.valueOf(seller.getRole()));
        sellerResponse.setPassword(seller.getPassword());
        sellerResponse.setName(seller.getName());
        sellerResponse.setLastname(seller.getLastName());
        sellerResponse.setDateOfBirth(seller.getDateOfBirth());
        sellerResponse.setCardNumber(seller.getCardNumber());
        return sellerResponse;
    }

    @Override
    public List<SellerResponse> toDtoS(List<Seller> all) {
        List<SellerResponse> sellerResponses = new ArrayList<>();
        for(Seller seller : all) {
            sellerResponses.add(toDto(seller));
        }
        return sellerResponses;
    }
}
