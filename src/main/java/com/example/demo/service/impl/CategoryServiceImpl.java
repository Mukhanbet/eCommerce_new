package com.example.demo.service.impl;

import com.example.demo.dto.category.CategoryRequest;
import com.example.demo.dto.category.CategoryResponse;
import com.example.demo.entities.Category;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // todo save the name of category in the upper case;
    @Override
    public List<CategoryResponse> getAll() {
        return categoryMapper.toDtoS(categoryRepository.findAll());
    }

    @Override
    public CategoryResponse findByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name.toUpperCase());
        checker(category, name);
        return categoryMapper.toDto(category.get());
    }

    @Override
    public void updateByName(String name, CategoryRequest categoryRequest) {
        Optional<Category> category = categoryRepository.findByName(name);
        checker(category, name);
        if(categoryRepository.findByName(categoryRequest.getName().toUpperCase()).isPresent()) {
            throw new BadCredentialsException("Category with name: " + categoryRequest.getName() + " have already exist");
        }
        category.get().setName(categoryRequest.getName().toUpperCase());
        categoryRepository.save(category.get());
    }

    @Override
    public void deleteByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        checker(category, name);
        categoryRepository.deleteByName(name.toUpperCase());
    }

    @Override
    public void create(CategoryRequest categoryRequest) {
        if(categoryRepository.findByName(categoryRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Category with name: " + categoryRequest.getName() + " have already exist");
        }
        Category category = new Category();
        category.setName(categoryRequest.getName().toUpperCase());
        categoryRepository.save(category);
    }

    private void checker(Optional<Category> category, String name) {
        if(category.isEmpty()) {
            throw new NotFoundException("Category with name: " + name + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
