package com.example.demo.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminRequest {
    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    private String portfolio;
}
