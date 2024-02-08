package com.example.demo.entities;

import com.example.demo.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;
    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    private String portfolio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "system", referencedColumnName = "id")
    private User user;
}
