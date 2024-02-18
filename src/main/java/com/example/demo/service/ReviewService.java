package com.example.demo.service;

import com.example.demo.dto.review.ReviewRequest;
import com.example.demo.dto.review.ReviewResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ReviewService {
    List<ReviewResponse> getAll();
    ReviewResponse findById(Long id);
    ReviewResponse updateById(Long id, ReviewRequest reviewRequest);
    ReviewResponse updateByFields(Long id, Map<String, Object> fields);
    void deleteById(Long id);
    void makeReview(String userEmail, Long productId, ReviewRequest reviewRequest);
}
