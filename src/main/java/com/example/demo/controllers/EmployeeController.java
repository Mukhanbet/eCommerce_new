package com.example.demo.controllers;

import com.example.demo.dto.employee.EmployeeRequest;
import com.example.demo.dto.employee.EmployeeResponse;
import com.example.demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/getAll")
    public List<EmployeeResponse> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/findByEmail/{email}")
    public EmployeeResponse findByEmail(@PathVariable String email) {
        return employeeService.findByEmail(email);
    }

    @PutMapping("/updateByEmail/{email}")
    public void updateByEmail(@PathVariable String email,@RequestBody EmployeeRequest employeeRequest) {
        employeeService.updateByEmail(email, employeeRequest);
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public void deleteByEmail(@PathVariable String email) {
        employeeService.deleteByEmail(email);
    }
}
