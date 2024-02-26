package com.example.demo.controllers;

import com.example.demo.dto.login.AuthLoginRequest;
import com.example.demo.dto.login.AuthLoginResponse;
import com.example.demo.dto.user.UserRequest;
import com.example.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthLoginResponse register(@RequestBody UserRequest userRequest) {
        return authService.register(userRequest);
    }

    @PostMapping("/login")
    public AuthLoginResponse login(@RequestBody AuthLoginRequest authLoginRequest) {
        return authService.login(authLoginRequest);
    }
}
