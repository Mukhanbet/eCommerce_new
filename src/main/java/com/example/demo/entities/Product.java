package com.example.demo.entities;

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
    private double discountPrice;
    private int amount;
    private String size;
    private String color;
    private boolean available;
    private LocalDate addedDate;
    @Transient
    private double rating;

    public double getRating() {
        if(reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        double sumStars = reviews.stream()
                .mapToDouble(Review::getStar)
                .sum();
        return sumStars/reviews.size();
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "name")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "name")
    private Brand brand;

    @OneToMany(mappedBy = "enrolProduct")
    private List<Basket> baskets;

    @OneToMany(mappedBy = "enrolProductToOrder")
    private List<Order> orders;

    @OneToMany(mappedBy = "enrolProductToReview")
    private List<Review> reviews;

    @ManyToOne
    private Discount discount;
}
