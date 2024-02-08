package com.example.demo.service;

import com.example.demo.dto.user.UserRequest;
import com.example.demo.dto.user.UserResponse;

import java.util.List;

public interface MyUserDetailsService {
    List<UserResponse> getAll();
    UserResponse getByEmail(String email);
    void updateByEmail(String email, UserRequest userRequest);
    void deleteByEmail(String email);
    void register(UserRequest userRequest);
}
