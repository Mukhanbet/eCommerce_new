package com.example.demo.service;

import com.example.demo.dto.review.ReviewRequest;
import com.example.demo.dto.review.ReviewResponse;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> getAll();
    ReviewResponse findById(Long id);
    ReviewResponse updateById(Long id, ReviewRequest reviewRequest);
    void deleteById(Long id);
    void makeReview(String userEmail, Long productId, ReviewRequest reviewRequest);
}
