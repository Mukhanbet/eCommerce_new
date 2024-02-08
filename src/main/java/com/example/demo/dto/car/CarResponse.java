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
    private int price;
    private String owner;
}
