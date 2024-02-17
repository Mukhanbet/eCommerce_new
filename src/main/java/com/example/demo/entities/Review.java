package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "review_tb")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewText;
    private LocalDate reviewDate;
    private double star;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User enrolUserToReview;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product enrolProductToReview;
}
