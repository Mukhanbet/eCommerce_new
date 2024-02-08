package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;
    private int year;
    private String country;
    private int price;

    // todo add here the phone number of Seller!

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "name")
    private Type type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "email")
    private Seller seller;
}
