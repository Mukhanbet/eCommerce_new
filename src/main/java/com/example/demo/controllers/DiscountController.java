package com.example.demo.controllers;

import com.example.demo.dto.discount.DiscountRequest;
import com.example.demo.dto.discount.DiscountResponse;
import com.example.demo.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/discount")
public class DiscountController {
    private final DiscountService discountService;

    @GetMapping("/getAll")
    public List<DiscountResponse> getAll() {
        return discountService.getAll();
    }

    @GetMapping("/findById/{id}")
    public DiscountResponse findById(@PathVariable Long id) {
        return discountService.findById(id);
    }

    @PutMapping("/updateById/{id}")
    public void updateById(@PathVariable Long id, @RequestBody DiscountRequest discountRequest) {
        discountService.updateById(id, discountRequest);
    }

    @PatchMapping("/updateByFields/{id}")
    public void updateByFields(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        discountService.updateByFields(id, fields);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Long id) {
        discountService.deleteById(id);
    }

    @PostMapping("/create")
    public void create(@RequestBody DiscountRequest discountRequest) {
        discountService.create(discountRequest);
    }
}
