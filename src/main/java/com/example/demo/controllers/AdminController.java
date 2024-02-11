package com.example.demo.controllers;

import com.example.demo.dto.admin.AdminRequest;
import com.example.demo.dto.admin.AdminResponse;
import com.example.demo.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/getAdmin")
    public AdminResponse getAdmin() {
        return adminService.getAdmin();
    }

    @PutMapping("/updateAdmin")
    public void updateAdmin(@RequestBody AdminRequest adminRequest) {
        adminService.updateAdmin(adminRequest);
    }
}
