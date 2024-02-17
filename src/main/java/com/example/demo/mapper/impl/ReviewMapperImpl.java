package com.example.demo.mapper.impl;

import com.example.demo.dto.review.ReviewResponse;
import com.example.demo.entities.Review;
import com.example.demo.mapper.ReviewMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapperImpl implements ReviewMapper {
    @Override
    public ReviewResponse toDto(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setId(review.getId());
        reviewResponse.setReviewText(review.getReviewText());
        reviewResponse.setReviewDate(review.getReviewDate());
        reviewResponse.setStar(review.getStar());
        reviewResponse.setUserEmail(review.getEnrolUserToReview().getEmail());
        reviewResponse.setProductId(review.getEnrolProductToReview().getId());
        return reviewResponse;
    }

    @Override
    public List<ReviewResponse> toDtoS(List<Review> all) {
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for(Review review : all) {
            reviewResponses.add(toDto(review));
        }
        return reviewResponses;
    }
}
