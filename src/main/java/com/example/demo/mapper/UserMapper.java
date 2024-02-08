package com.example.demo.mapper;

import com.example.demo.dto.user.UserResponse;
import com.example.demo.entities.User;

import java.util.List;

public interface UserMapper {
    UserResponse toDto(User user);
    List<UserResponse> toDtoS(List<User> all);
}
