package com.example.demo.service;

import com.example.demo.dto.manager.ManagerRequest;
import com.example.demo.dto.manager.ManagerResponse;

import java.util.List;

public interface ManagerService {
    List<ManagerResponse> getAll();
    ManagerResponse findByEmail(String name);
    void updateByEmail(String name, ManagerRequest managerRequest);
    void deleteByEmail(String name);
}
