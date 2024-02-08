package com.example.demo.mapper;

import com.example.demo.dto.employee.EmployeeResponse;
import com.example.demo.entities.Employee;

import java.util.List;

public interface EmployeeMapper {
    EmployeeResponse toDto(Employee employee);
    List<EmployeeResponse> toDtoS(List<Employee> all);
}
