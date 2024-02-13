package com.example.demo.controllers;

import com.example.demo.dto.product.ProductRequest;
import com.example.demo.dto.product.ProductResponse;
import com.example.demo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAll")
    public List<ProductResponse> getAll() {
        return productService.getAll();
    }

    @GetMapping("/getAvailableProduct")
    public List<ProductResponse> getAllAvailableProducts() {
        return productService.getAllAvailableProducts();
    }

    @GetMapping("/getSolvedProduct")
    public List<ProductResponse> getSolvedProducts() {
        return productService.getSolvedProducts();
    }

    @GetMapping("/getProductsSortedByPrice/{order}")
    public List<ProductResponse> getProductsSortedByPrice(@PathVariable String order) {
        return productService.getProductsSortedByPrice(order);
    }

    @GetMapping("/getByType/{type}")
    public List<ProductResponse> getByType(@PathVariable String type) {
        return productService.getByType(type);
    }

    @GetMapping("/compareWith/{firstId}/{secondId}")
    public List<String> compareWith(@PathVariable Long firstId, @PathVariable Long secondId) {
        return productService.compareWith(firstId, secondId);
    }

    @GetMapping("/getSolvedProductByType/{type}")
    public List<ProductResponse> getSolvedProductsByType(@PathVariable String type) {
        return productService.getSolvedProductsByType(type);
    }

    @GetMapping("/findById/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PutMapping("/putTheProductToBasket/{productId}/user/{userEmail}")
    public void putTheProductToBasket(@PathVariable Long productId, @PathVariable String userEmail) {
        productService.putTheProductToBasket(productId, userEmail);
    }

    @PutMapping("/updateById/{id}")
    public void updateById(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        productService.updateById(id, productRequest);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping("/create/{sellerEmail}")
    public void create(@RequestBody ProductRequest productRequest, @PathVariable String sellerEmail) {
        productService.create(productRequest, sellerEmail);
    }
}
