package com.example.demo.mapper.impl;

import com.example.demo.dto.manager.ManagerResponse;
import com.example.demo.entities.Manager;
import com.example.demo.mapper.ManagerMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ManagerMapperImpl implements ManagerMapper {
    @Override
    public ManagerResponse toDto(Manager manager) {
        ManagerResponse managerResponse = new ManagerResponse();
        managerResponse.setId(manager.getId());
        managerResponse.setEmail(manager.getEmail());
        managerResponse.setRole(String.valueOf(manager.getRole()));
        managerResponse.setPassword(manager.getPassword());
        managerResponse.setName(manager.getName());
        managerResponse.setLastname(manager.getLastName());
        managerResponse.setDateOfBirth(manager.getDateOfBirth());
        managerResponse.setCardNumber(manager.getCardNumber());
        managerResponse.setPhoneNumber(manager.getPhoneNumber());
        return managerResponse;
    }

    @Override
    public List<ManagerResponse> toDtoS(List<Manager> all) {
        List<ManagerResponse> managerRespons = new ArrayList<>();
        for(Manager manager : all) {
            managerRespons.add(toDto(manager));
        }
        return managerRespons;
    }
}
