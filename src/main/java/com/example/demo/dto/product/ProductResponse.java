package com.example.demo.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private String type;
    private String color;
    private int year;
    private String country;
    private double price;
    private String owner;
    private boolean available;
    private int amount;
}
