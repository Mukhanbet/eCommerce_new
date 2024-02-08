package com.example.demo.mapper;

import com.example.demo.dto.seller.SellerResponse;
import com.example.demo.entities.Seller;

import java.util.List;

public interface SellerMapper {
    SellerResponse toDto(Seller seller);
    List<SellerResponse> toDtoS(List<Seller> all);
}
