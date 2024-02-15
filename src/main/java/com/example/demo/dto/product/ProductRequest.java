package com.example.demo.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String description;
    private double price;
    private int amount;
    private String category;
    private String brand;
    private String size;
    private String color;
    private boolean available;
}
