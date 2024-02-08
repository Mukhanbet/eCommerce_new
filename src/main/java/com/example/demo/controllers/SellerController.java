package com.example.demo.controllers;

import com.example.demo.dto.seller.SellerRequest;
import com.example.demo.dto.seller.SellerResponse;
import com.example.demo.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/seller")
public class SellerController {
    private final SellerService sellerService;

    @GetMapping("/getAll")
    public List<SellerResponse> getAll() {
        return sellerService.getAll();
    }

    @GetMapping("/findByEmail/{email}")
    public SellerResponse findByEmail(@PathVariable String email) {
        return sellerService.findByEmail(email);
    }

    @PutMapping("/updateByEmail/{email}")
    public void updateByEmail(@PathVariable String email,@RequestBody SellerRequest sellerRequest) {
        sellerService.updateByEmail(email, sellerRequest);
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public void deleteByEmail(@PathVariable String email) {
        sellerService.deleteByEmail(email);
    }
}
