package com.example.demo.mapper.impl;

import com.example.demo.dto.category.CategoryResponse;
import com.example.demo.entities.Category;
import com.example.demo.mapper.CategoryMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public CategoryResponse toDto(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> toDtoS(List<Category> all) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for(Category category : all) {
            categoryResponses.add(toDto(category));
        }
        return categoryResponses;
    }
}
