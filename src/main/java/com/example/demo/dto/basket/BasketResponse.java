package com.example.demo.dto.basket;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BasketResponse {
    private Long id;
    private String userEmail;
    private String product;
    private double totalSum;
    private LocalDate createdDay;
    private LocalDate endDay;
    private int amount;
}
