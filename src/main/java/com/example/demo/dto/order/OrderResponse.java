package com.example.demo.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private String userEmail;
    // todo here should we have to write some info about user?
    private Long productId;
    // todo also here some info
    private int amount;
    private String address;
    private String payment;
    private double sum;
    private LocalDate dateOfOrder;
    private String status;
}
