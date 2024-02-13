package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private double price;
    private boolean available;
    private int amount;

    // todo add here the phone number of Seller!

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "name")
    private Type type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "email")
    private Seller seller;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "basket_tb",
            joinColumns = @JoinColumn(name = "car", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_tb_email", referencedColumnName = "email")
    )
    private List<User> enrolUsersToCars;
}
