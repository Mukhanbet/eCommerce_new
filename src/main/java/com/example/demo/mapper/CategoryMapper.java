package com.example.demo.mapper;

import com.example.demo.dto.category.CategoryResponse;
import com.example.demo.entities.Category;

import java.util.List;

public interface CategoryMapper {
    CategoryResponse toDto(Category category);
    List<CategoryResponse> toDtoS(List<Category> all);

}
