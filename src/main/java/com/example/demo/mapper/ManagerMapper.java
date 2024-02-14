package com.example.demo.mapper;

import com.example.demo.dto.manager.ManagerResponse;
import com.example.demo.entities.Manager;

import java.util.List;

public interface ManagerMapper {
    ManagerResponse toDto(Manager manager);
    List<ManagerResponse> toDtoS(List<Manager> all);
}
