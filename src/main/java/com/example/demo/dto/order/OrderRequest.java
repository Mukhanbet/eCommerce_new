package com.example.demo.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private int amount;
    private String address;
    private String payment;
}
