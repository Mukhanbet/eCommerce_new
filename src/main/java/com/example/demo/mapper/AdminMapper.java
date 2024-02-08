package com.example.demo.mapper;

import com.example.demo.dto.admin.AdminResponse;
import com.example.demo.entities.Admin;

import java.util.List;

public interface AdminMapper {
    AdminResponse toDto(Admin admin);
    List<AdminResponse> toDtoS(List<Admin> all);
}
