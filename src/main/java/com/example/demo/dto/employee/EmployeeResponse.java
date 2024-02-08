package com.example.demo.dto.employee;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeResponse {
    private Long id;
    private String email;
    private String role;
    private String password;
    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    private String portfolio;
}
