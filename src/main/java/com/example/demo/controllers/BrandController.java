package com.example.demo.controllers;

import com.example.demo.dto.brand.BrandRequest;
import com.example.demo.dto.brand.BrandResponse;
import com.example.demo.service.BrandService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    @GetMapping("/getAll")
    public List<BrandResponse> getAll() {
        return brandService.getAll();
    }

    @GetMapping("/findByName/{name}")
    public BrandResponse findByName(@PathVariable String name) {
        return brandService.findByName(name);
    }

    @PutMapping("/updateByName/{name}")
    public void updateByName(@PathVariable String name, @RequestBody BrandRequest brandRequest) {
        brandService.updateByName(name, brandRequest);
    }

    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        brandService.deleteByName(name);
    }

    @PostMapping("/create")
    public void create(@RequestBody BrandRequest brandRequest) {
         brandService.create(brandRequest);
    }
}
