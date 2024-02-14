package com.example.demo.service.impl;

import com.example.demo.dto.manager.ManagerRequest;
import com.example.demo.dto.manager.ManagerResponse;
import com.example.demo.entities.Manager;
import com.example.demo.entities.User;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.ManagerMapper;
import com.example.demo.repositories.ManagerRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.ManagerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;
    private final UserRepository userRepository;
    @Override
    public List<ManagerResponse> getAll() {
        return managerMapper.toDtoS(managerRepository.findAll());
    }

    @Override
    public ManagerResponse findByEmail(String email) {
        Optional<Manager> manager = managerRepository.findByEmail(email);
        checker(manager, email);
        return managerMapper.toDto(manager.get());
    }


    @Override
    public void updateByEmail(String email, ManagerRequest managerRequest) {
        Optional<Manager> manager = managerRepository.findByEmail(email);
        checker(manager, email);
        if(userRepository.findByEmail(managerRequest.getEmail()).isPresent() && !managerRequest.getEmail().equals(email)) {
            throw new BadCredentialsException("User with email: " + managerRequest.getEmail() + " have already exist!");
        }
        manager.get().setName(managerRequest.getName());
        manager.get().setLastName(managerRequest.getLastName());
        manager.get().setDateOfBirth(managerRequest.getDateOfBirth());
        manager.get().setCardNumber(managerRequest.getCardNumber());
        manager.get().setPhoneNumber(managerRequest.getPhoneNumber());
        User user = manager.get().getUser();
        user.setEmail(managerRequest.getEmail());
        user.setPassword(managerRequest.getPassword());
        manager.get().setUser(user);
        managerRepository.save(manager.get());
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<Manager> manager = managerRepository.findByEmail(email);
        checker(manager, email);
        managerRepository.deleteByEmail(email);
    }

    private void checker(Optional<Manager> manager, String email) {
        if(manager.isEmpty()) {
            throw new NotFoundException("manager with email: " + email + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
