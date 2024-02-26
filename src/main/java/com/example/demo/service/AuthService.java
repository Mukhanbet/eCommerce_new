package com.example.demo.service;

import com.example.demo.dto.login.AuthLoginRequest;
import com.example.demo.dto.login.AuthLoginResponse;
import com.example.demo.dto.user.UserRequest;

public interface AuthService {
    AuthLoginResponse register(UserRequest userRequest);
    AuthLoginResponse login(AuthLoginRequest authLoginRequest);
}
