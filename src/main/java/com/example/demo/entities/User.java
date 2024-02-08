package com.example.demo.entities;

import com.example.demo.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Admin admin;

    @OneToOne(cascade = CascadeType.ALL)
    private Seller seller;

    @OneToOne(cascade = CascadeType.ALL)
    private Employee employee;
}
