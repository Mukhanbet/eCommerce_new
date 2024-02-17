package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    private String size;
    private String color;
    private boolean available;
    private LocalDate addedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "name")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "name")
    private Brand brand;

    @OneToMany(mappedBy = "enrolProduct")
    private List<Basket> baskets;
}
