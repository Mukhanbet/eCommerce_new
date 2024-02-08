package com.example.demo.service.impl;

import com.example.demo.dto.user.UserRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.entities.Admin;
import com.example.demo.entities.Employee;
import com.example.demo.entities.Seller;
import com.example.demo.entities.User;
import com.example.demo.enums.Role;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.SellerRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SellerRepository sellerRepository;
    private final EmployeeRepository employeeRepository;
    @Override
    public List<UserResponse> getAll() {
        return userMapper.toDtoS(userRepository.findAll());
    }

    @Override
    public UserResponse getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        checker(user, email);
        return userMapper.toDto(user.get());
    }

    @Override
    public void updateByEmail(String email, UserRequest userRequest) {
        Optional<User> user = userRepository.findByEmail(email);
        checker(user, email);
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent() && !userRequest.getEmail().equals(email)) {
            throw new BadCredentialsException("User with email: " + userRequest.getEmail() + " have already exist!");
        }
        user.get().setEmail(userRequest.getEmail());
        user.get().setPassword(userRequest.getPassword());
        if(userRequest.getRole().equals(String.valueOf(Role.ADMIN))) {
            Admin admin = user.get().getAdmin();
            user.get().setRole(Role.valueOf(userRequest.getRole()));
            user.get().setAdmin(admin);
        } else if(userRequest.getRole().equals(String.valueOf(Role.SELLER))) {
            Seller seller = user.get().getSeller();
            user.get().setRole(Role.valueOf(userRequest.getRole()));
            seller.setEmail(userRequest.getEmail());
            seller.setRole(Role.valueOf(userRequest.getRole()));
            seller.setPassword(userRequest.getPassword());
            user.get().setSeller(seller);
            seller.setUser(user.get());
            sellerRepository.save(seller);
        } else if(userRequest.getRole().equals(String.valueOf(Role.EMPLOYEE))) {
            Employee employee = user.get().getEmployee();
            user.get().setRole(Role.valueOf(userRequest.getRole()));
            user.get().setEmployee(employee);
        }
        userRepository.save(user.get());
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        checker(user, email);
        userRepository.deleteByEmail(email);

    }

    @Override
    public void register(UserRequest userRequest) {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new BadCredentialsException("User with email: " + userRequest.getEmail() + " have already exist!");
        }
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        if(userRequest.getRole().equals(String.valueOf(Role.ADMIN))) {
            Admin admin = user.getAdmin();
            user.setRole(Role.valueOf(userRequest.getRole()));
            user.setAdmin(admin);
        } else if(userRequest.getRole().equals(String.valueOf(Role.SELLER))) {
            Seller seller = new Seller();
            user.setRole(Role.valueOf(userRequest.getRole()));
            seller.setEmail(userRequest.getEmail());
            seller.setRole(Role.valueOf(userRequest.getRole()));
            seller.setPassword(userRequest.getPassword());
            seller.setUser(user);
            user.setSeller(seller);
            sellerRepository.save(seller);
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
        userRepository.save(user);

    }

    private void checker(Optional<User> user, String email) {
        if(user.isEmpty()) {
            throw new NotFoundException("User with email: " + email + " not found!", HttpStatus.NOT_FOUND);
        }
    }
}
