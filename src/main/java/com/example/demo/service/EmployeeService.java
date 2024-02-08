package com.example.demo.service;

import com.example.demo.dto.employee.EmployeeRequest;
import com.example.demo.dto.employee.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> getAll();
    EmployeeResponse findByEmail(String email);
    void updateByEmail(String email, EmployeeRequest employeeRequest);
    void deleteByEmail(String email);
}
