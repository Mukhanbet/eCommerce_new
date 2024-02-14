package com.example.demo.service;

import com.example.demo.dto.category.CategoryRequest;
import com.example.demo.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();
    CategoryResponse findByName(String name);
    void updateByName(String name, CategoryRequest categoryRequest);
    void deleteByName(String name);
    void create(CategoryRequest categoryRequest);
}
