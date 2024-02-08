package com.example.demo.mapper.impl;

import com.example.demo.dto.employee.EmployeeResponse;
import com.example.demo.entities.Employee;
import com.example.demo.mapper.EmployeeMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {
    @Override
    public EmployeeResponse toDto(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employee.getId());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setRole(String.valueOf(employee.getRole()));
        employeeResponse.setPassword(employee.getPassword());
        employeeResponse.setName(employee.getName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setDateOfBirth(employee.getDateOfBirth());
        employeeResponse.setPortfolio(employee.getPortfolio());
        employeeResponse.setName(employee.getName());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setRole(String.valueOf(employee.getRole()));
        employeeResponse.setPassword(employee.getPassword());
        return employeeResponse;
    }

    @Override
    public List<EmployeeResponse> toDtoS(List<Employee> all) {
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        for(Employee employee : all) {
            employeeResponses.add(toDto(employee));
        }
        return employeeResponses;
    }
}
