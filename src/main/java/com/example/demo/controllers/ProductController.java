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

    @GetMapping("/getByCategory/{category}")
    public List<ProductResponse> getByCategory(@PathVariable String category) {
        return productService.getByCategory(category);
    }

    @GetMapping("/compareWith/{firstId}/{secondId}")
    public List<String> compareWith(@PathVariable Long firstId, @PathVariable Long secondId) {
        return productService.compareWith(firstId, secondId);
    }

    @GetMapping("/getSolvedProductByCategory/{category}")
    public List<ProductResponse> getSolvedProductsByCategory(@PathVariable String category) {
        return productService.getSolvedProductsByCategory(category);
    }

    @GetMapping("/findById/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PutMapping("/updateById/{id}")
    public void updateById(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        productService.updateById(id, productRequest);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping("/create")
    public void create(@RequestBody ProductRequest productRequest) {
        productService.create(productRequest);
    }
}
