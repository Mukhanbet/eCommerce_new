package com.example.demo.dto.car;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarRequest {
    private String name;
    private String type;
    private String color;
    private int year;
    private String country;
    private double price;
    private boolean available;
    private int amount;
}
