package com.example.demo.dto.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginResponse {
    private Long id;
    private String name;
    private String email;
    private String token;
}
