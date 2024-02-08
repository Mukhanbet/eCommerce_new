package com.example.demo.controllers;

import com.example.demo.dto.user.UserRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final MyUserDetailsService service;

    @GetMapping("/getAll")
    public List<UserResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/getByEmail/{email}")
    public UserResponse getByEmail(@PathVariable String email) {
        return service.getByEmail(email);
    }

    @PutMapping("/updateByEmail/{email}")
    public void updateByEmail(@PathVariable String email,@RequestBody UserRequest userRequest) {
        service.updateByEmail(email, userRequest);
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public void deleteByEmail(@PathVariable String email) {
        service.deleteByEmail(email);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRequest userRequest) {
        service.register(userRequest);
        return "user saved!";
    }
}
