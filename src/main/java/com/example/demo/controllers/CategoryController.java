package com.example.demo.controllers;

import com.example.demo.dto.category.CategoryRequest;
import com.example.demo.dto.category.CategoryResponse;
import com.example.demo.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAll")
    public List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/findByName/{name}")
    public CategoryResponse findByName(@PathVariable String name) {
        return categoryService.findByName(name);
    }

    @PutMapping("/updateByName/{name}")
    public void updateByName(@PathVariable String name,@RequestBody CategoryRequest categoryRequest) {
        categoryService.updateByName(name, categoryRequest);
    }

    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        categoryService.deleteByName(name);
    }

    @PostMapping("/create")
    public void create(@RequestBody CategoryRequest categoryRequest) {
        categoryService.create(categoryRequest);
    }
}
