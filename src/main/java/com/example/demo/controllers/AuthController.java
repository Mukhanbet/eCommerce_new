package com.example.demo.controllers;

import com.example.demo.dto.login.AuthLoginRequest;
import com.example.demo.dto.login.AuthLoginResponse;
import com.example.demo.dto.user.UserRequest;
import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthLoginResponse register(@RequestBody UserRequest userRequest) {
        return authService.register(userRequest);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest authLoginRequest) {
        try {
            return new ResponseEntity<>(authService.login(authLoginRequest), HttpStatus.OK);
        } catch (ExpiredJwtException ex) {
            return new ResponseEntity<>("Token expired", HttpStatus.UNAUTHORIZED);
        }
    }
}
