package com.example.demo.mapper;

import com.example.demo.dto.review.ReviewResponse;
import com.example.demo.entities.Review;

import java.util.List;

public interface ReviewMapper {
    ReviewResponse toDto(Review review);
    List<ReviewResponse> toDtoS(List<Review> all);
}
