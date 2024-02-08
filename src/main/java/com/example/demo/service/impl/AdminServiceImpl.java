package com.example.demo.service.impl;

import com.example.demo.dto.admin.AdminRequest;
import com.example.demo.dto.admin.AdminResponse;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public AdminResponse getAdmin() {
        return null;
    }

    @Override
    public void updateAdmin(AdminRequest adminRequest) {

    }
}
