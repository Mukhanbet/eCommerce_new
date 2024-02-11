package com.example.demo.service.impl;

import com.example.demo.dto.admin.AdminRequest;
import com.example.demo.dto.admin.AdminResponse;
import com.example.demo.entities.Admin;
import com.example.demo.entities.User;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final UserRepository userRepository;

    @Override
    public AdminResponse getAdmin() {
        Optional<Admin> admin = adminRepository.findById(1L);
        return adminMapper.toDto(admin.get());
    }

    @Override
    public void updateAdmin(AdminRequest adminRequest) {
        Optional<Admin> admin = adminRepository.findById(1L);
        admin.get().setName(adminRequest.getName());
        admin.get().setLastName(adminRequest.getLastName());
        admin.get().setDateOfBirth(adminRequest.getDateOfBirth());
        admin.get().setPortfolio(adminRequest.getPortfolio());
        admin.get().setEmail(adminRequest.getEmail());
        admin.get().setPassword(adminRequest.getPassword());

        User user = admin.get().getUser();
        user.setEmail(adminRequest.getEmail());
        user.setPassword(adminRequest.getPassword());
        admin.get().setUser(user);
        user.setAdmin(admin.get());
        adminRepository.save(admin.get());
        userRepository.save(user);
    }
}
