package com.example.demo.service;

import com.example.demo.dto.seller.SellerRequest;
import com.example.demo.dto.seller.SellerResponse;

import java.util.List;

public interface SellerService {
    List<SellerResponse> getAll();
    SellerResponse findByEmail(String name);
    void updateByEmail(String name, SellerRequest sellerRequest);
    void deleteByEmail(String name);
}
