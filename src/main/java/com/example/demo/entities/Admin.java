package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Admin {

    //some changes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String role;
    private String password;
    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    private String portfolio;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
