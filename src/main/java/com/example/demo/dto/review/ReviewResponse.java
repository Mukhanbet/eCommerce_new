package com.example.demo.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReviewResponse {
    private Long id;
    private String reviewText;
    private LocalDate reviewDate;
    private double star;
    private String userEmail;
    private Long productId;
}
