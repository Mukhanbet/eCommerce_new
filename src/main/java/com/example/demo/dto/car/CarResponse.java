package com.example.demo.dto.car;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponse {
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
