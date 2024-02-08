package com.example.demo.mapper.impl;


import com.example.demo.dto.admin.AdminResponse;
import com.example.demo.entities.Admin;
import com.example.demo.mapper.AdminMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminMapperImpl implements AdminMapper {
    @Override
    public AdminResponse toDto(Admin admin) {
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setId(admin.getId());
        adminResponse.setName(admin.getName());
//        adminResponse.setEmail(admin.getEmail());
//        adminResponse.setRole(String.valueOf(admin.getRole()));
//        adminResponse.setPassword(admin.getPassword());
        adminResponse.setEmail(admin.getEmail());
        adminResponse.setRole(String.valueOf(admin.getRole()));
        adminResponse.setPassword(admin.getPassword());
        return adminResponse;
    }

    @Override
    public List<AdminResponse> toDtoS(List<Admin> all) {
        List<AdminResponse> adminResponses = new ArrayList<>();
        for (Admin admin : all) {
            adminResponses.add(toDto(admin));
        }
        return adminResponses;
    }
}
