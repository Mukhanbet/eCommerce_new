package com.example.demo.controllers;

import com.example.demo.dto.review.ReviewRequest;
import com.example.demo.dto.review.ReviewResponse;
import com.example.demo.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/getAll")
    public List<ReviewResponse> getAll() {
        return reviewService.getAll();
    }

    @GetMapping("/findById/{id}")
    public ReviewResponse findById(@PathVariable Long id) {
        return reviewService.findById(id);
    }

    @PutMapping("/updateById/{id}")
    public ReviewResponse updateById(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest) {
        return reviewService.updateById(id, reviewRequest);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Long id) {
        reviewService.deleteById(id);
    }

    @PostMapping("/makeReview/user/userEmail/{userEmail}/product/{productId}")
    public void makeReview(@PathVariable String userEmail, @PathVariable Long productId, @RequestBody ReviewRequest reviewRequest) {
        reviewService.makeReview(userEmail, productId, reviewRequest);
    }
}
