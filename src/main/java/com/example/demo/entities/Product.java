package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private int amount;
    private String color;
    private int year;
    private String country;
    private boolean available;

    // todo add here the phone number of Seller!

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "name")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "email")
    private Manager manager;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "basket_tb",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_email", referencedColumnName = "email")
    )
    private List<User> enrolUsersToProducts;
}
