package com.example.demo.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String email;
    private String role;
    private String password;
}
