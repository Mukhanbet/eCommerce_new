package com.example.demo.dto.seller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SellerResponse {
    private Long id;
    private String email;
    private String role;
    private String password;
    private String name;
    private String lastname;
    private LocalDate dateOfBirth;
    private int cardNumber;
    private String phoneNumber;
}
