package com.example.demo.service.impl;

import com.example.demo.dto.review.ReviewRequest;
import com.example.demo.dto.review.ReviewResponse;
import com.example.demo.entities.Product;
import com.example.demo.entities.Review;
import com.example.demo.entities.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.ReviewRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public List<ReviewResponse> getAll() {
        return reviewMapper.toDtoS(reviewRepository.findAll());
    }

    @Override
    public ReviewResponse findById(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        checker(review, id);
        return reviewMapper.toDto(review.get());
    }

    @Override
    public ReviewResponse updateById(Long id, ReviewRequest reviewRequest) {
        Optional<Review> review = reviewRepository.findById(id);
        checker(review, id);
        review.get().setReviewText(reviewRequest.getReviewText());
        review.get().setStar(reviewRequest.getStar());
        review.get().setReviewDate(LocalDate.now());
        // todo add here user and product
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        checker(review, id);
        reviewRepository.deleteById(id);
    }

    @Override
    public void makeReview(String userEmail, Long productId, ReviewRequest reviewRequest) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isEmpty()) {
            throw new NotFoundException("User with email \"" + userEmail + "\" not found", HttpStatus.NOT_FOUND);
        }

        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()) {
            throw new NotFoundException("Product with id \"" + productId + "\" not found", HttpStatus.NOT_FOUND);
        }

        Review review = new Review();
        for(Review review1 : reviewRepository.findAll()) {
            if(review1.getEnrolUserToReview().getEmail().equals(userEmail) && review1.getEnrolProductToReview().getId().equals(productId)) {
                review = review1;
            }
        }
        review.setReviewText(reviewRequest.getReviewText());
        review.setStar(reviewRequest.getStar());
        review.setReviewDate(LocalDate.now());
        review.setEnrolUserToReview(user.get());
        review.setEnrolProductToReview(product.get());
        reviewRepository.save(review);
    }

    private void checker(Optional<Review> review, Long id) {
        if(review.isEmpty()) {
            throw new NotFoundException("Review with id \"" + id + "\" not found", HttpStatus.NOT_FOUND);
        }
    }
}
