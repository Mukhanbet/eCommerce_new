package com.example.demo.dto.manager;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ManagerRequest {
    private String email;
//    private String role;
    private String password;
    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    private int cardNumber;
    private String phoneNumber;
}
