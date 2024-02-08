package com.example.demo.service;

import com.example.demo.dto.type.TypeRequest;
import com.example.demo.dto.type.TypeResponse;

import java.util.List;

public interface TypeService {
    List<TypeResponse> getAll();
    TypeResponse findByName(String name);
    void updateByName(String name, TypeRequest typeRequest);
    void deleteByName(String name);
    void create(TypeRequest typeRequest);
}
