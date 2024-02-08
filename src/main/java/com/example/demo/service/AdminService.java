package com.example.demo.service;

import com.example.demo.dto.admin.AdminRequest;
import com.example.demo.dto.admin.AdminResponse;

public interface AdminService {
    AdminResponse getAdmin();
    void updateAdmin(AdminRequest adminRequest);
}