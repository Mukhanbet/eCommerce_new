package com.example.demo.dto.discount;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiscountResponse {
    private Long id;
    private String name;
    private int discount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
