package com.example.demo.service.impl;

import com.example.demo.dto.employee.EmployeeRequest;
import com.example.demo.dto.employee.EmployeeResponse;
import com.example.demo.entities.Employee;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UserRepository userRepository;
    @Override
    public List<EmployeeResponse> getAll() {
        return employeeMapper.toDtoS(employeeRepository.findAll());
    }

    @Override
    public EmployeeResponse findByEmail(String email) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        checker(employee, email);
        return employeeMapper.toDto(employee.get());
    }

    @Override
    public void updateByEmail(String email, EmployeeRequest employeeRequest) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        checker(employee, email);
        if(userRepository.findByEmail(employeeRequest.getEmail()).isPresent() && !employeeRequest.getEmail().equals(email)) {
            throw new BadCredentialsException("User with email: " + employeeRequest.getEmail() + " have already exist!");
        }
        employee.get().setEmail(employeeRequest.getEmail());
//        employee.get().setRole(Role.valueOf(employeeRequest.getRole())); todo will it correct if we will change the role here to another
        employee.get().setPassword(employeeRequest.getPassword());
        employee.get().setName(employeeRequest.getName());
        employee.get().setLastName(employeeRequest.getLastName());
        employee.get().setDateOfBirth(employeeRequest.getDateOfBirth());
        employee.get().setPortfolio(employeeRequest.getPortfolio());
        employeeRepository.save(employee.get());
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        checker(employee, email);
        employeeRepository.deleteByEmail(email);
    }

    private void checker(Optional<Employee> employee, String email) {
        if(employee.isEmpty()) {
            throw new NotFoundException("Employee with email: " + email + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
