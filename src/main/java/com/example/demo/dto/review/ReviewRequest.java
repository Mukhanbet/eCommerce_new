package com.example.demo.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private String reviewText;
    private double star;
}
