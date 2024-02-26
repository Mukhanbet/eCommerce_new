package com.example.demo.service.impl;

import com.example.demo.config.JwtService;
import com.example.demo.dto.login.AuthLoginRequest;
import com.example.demo.dto.login.AuthLoginResponse;
import com.example.demo.dto.user.UserRequest;
import com.example.demo.entities.Admin;
import com.example.demo.entities.Employee;
import com.example.demo.entities.Manager;
import com.example.demo.entities.User;
import com.example.demo.enums.Role;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.ManagerRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ManagerRepository managerRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthLoginResponse register(UserRequest userRequest) {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new BadCredentialsException("User with email: " + userRequest.getEmail() + " have already exist!");
        }
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        if(userRequest.getRole().equals(String.valueOf(Role.ADMIN))) {
            Admin admin = user.getAdmin();
            user.setRole(Role.valueOf(userRequest.getRole()));
            user.setAdmin(admin);
        } else if(userRequest.getRole().equals(String.valueOf(Role.MANAGER))) {
            Manager manager = new Manager();
            user.setRole(Role.valueOf(userRequest.getRole()));
            manager.setEmail(userRequest.getEmail());
            manager.setRole(Role.valueOf(userRequest.getRole()));
            manager.setPassword(userRequest.getPassword());
            manager.setUser(user);
            user.setManager(manager);
            managerRepository.save(manager);
        } else if(userRequest.getRole().equals(String.valueOf(Role.EMPLOYEE))) {
            Employee employee = new Employee();
            user.setRole(Role.valueOf(userRequest.getRole()));
            employee.setEmail(userRequest.getEmail());
            employee.setRole(Role.valueOf(userRequest.getRole()));
            employee.setPassword(userRequest.getPassword());
            employee.setUser(user);
            user.setEmployee(employee);
            employeeRepository.save(employee);
        }
        String token = jwtService.generateToken(user);
        userRepository.save(user);
        AuthLoginResponse authLoginResponse = new AuthLoginResponse();
        authLoginResponse.setToken(token);
        return authLoginResponse;
    }

    @Override
    public AuthLoginResponse login(AuthLoginRequest authLoginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authLoginRequest.getEmail(),
                        authLoginRequest.getPassword()
                )
        );
        Optional<User> user = userRepository.findByEmail(authLoginRequest.getEmail());
        if(user.isEmpty()) {
            throw new NotFoundException("User with email \"" + authLoginRequest.getEmail() + "\" not found", HttpStatus.NOT_FOUND);
        }
        String token = jwtService.generateToken(user.get());
        AuthLoginResponse authLoginResponse = new AuthLoginResponse();
        authLoginResponse.setToken(token);
        return authLoginResponse;
    }
}
