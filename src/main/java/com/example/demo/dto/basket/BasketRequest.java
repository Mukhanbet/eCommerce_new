package com.example.demo.dto.basket;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BasketRequest {
    private double totalSum;
    private LocalDate createdDay;
    private String status;
    private String delivery;
    private String payment;
}
