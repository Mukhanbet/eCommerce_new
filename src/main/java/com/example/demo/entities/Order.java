package com.example.demo.entities;

import com.example.demo.enums.Payment;
import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "order_tb")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;
    private String address;
    @Enumerated(EnumType.STRING)
    private Payment payment;
    private double sum;
    private LocalDate dateOfOrder;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User enrolUserToOrder;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product enrolProductToOrder;
}
