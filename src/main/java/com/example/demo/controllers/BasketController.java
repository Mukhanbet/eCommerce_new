package com.example.demo.controllers;

import com.example.demo.dto.basket.BasketRequest;
import com.example.demo.dto.basket.BasketResponse;
import com.example.demo.service.BasketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;

    @GetMapping("/getAll")
    public List<BasketResponse> getAll() {
        return basketService.getAll();
    }

    @GetMapping("/findById/{id}")
    public BasketResponse findById(@PathVariable Long id) {
        return basketService.findById(id);
    }

    @PutMapping("/updateById/{id}")
    public void updateById(@PathVariable Long id, @RequestBody BasketRequest basketRequest) {
        basketService.updateById(id, basketRequest);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Long id) {
        basketService.deleteById(id);
    }

    @PostMapping("/putProductToBasket/{productId}/user/{userEmail}")
    public void putProductToBasket(@PathVariable Long productId, @PathVariable String userEmail, @RequestBody BasketRequest basketRequest) {
        basketService.putProductToBasket(productId, userEmail, basketRequest);
    }
}
