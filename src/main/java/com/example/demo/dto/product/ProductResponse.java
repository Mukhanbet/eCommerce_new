package com.example.demo.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int discount;
    private int amount;
    private String category;
    private String brand;
    private String size;
    private String color;
    private boolean available;
    private double rating;
}
