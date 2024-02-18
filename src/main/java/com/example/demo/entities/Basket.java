package com.example.demo.entities;

import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalSum;
    private LocalDate createdDay;
    private LocalDate endDay;
    private int amount;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User enrolUser;

    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product enrolProduct;
}
