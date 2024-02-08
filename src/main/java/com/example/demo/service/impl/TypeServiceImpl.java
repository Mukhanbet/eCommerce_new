package com.example.demo.service.impl;

import com.example.demo.dto.type.TypeRequest;
import com.example.demo.dto.type.TypeResponse;
import com.example.demo.entities.Type;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.TypeMapper;
import com.example.demo.repositories.TypeRepository;
import com.example.demo.service.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    // todo save the name of type in the upper case;
    @Override
    public List<TypeResponse> getAll() {
        return typeMapper.toDtoS(typeRepository.findAll());
    }

    @Override
    public TypeResponse findByName(String name) {
        Optional<Type> type = typeRepository.findByName(name.toUpperCase());
        checker(type, name);
        return typeMapper.toDto(type.get());
    }

    @Override
    public void updateByName(String name, TypeRequest typeRequest) {
        Optional<Type> type = typeRepository.findByName(name);
        checker(type, name);
        if(typeRepository.findByName(typeRequest.getName().toUpperCase()).isPresent()) {
            throw new BadCredentialsException("Type with name: " + typeRequest.getName() + " have already exist");
        }
        type.get().setName(typeRequest.getName().toUpperCase());
        typeRepository.save(type.get());
    }

    @Override
    public void deleteByName(String name) {
        Optional<Type> type = typeRepository.findByName(name);
        checker(type, name);
        typeRepository.deleteByName(name.toUpperCase());
    }

    @Override
    public void create(TypeRequest typeRequest) {
        if(typeRepository.findByName(typeRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Type with name: " + typeRequest.getName() + " have already exist");
        }
        Type type = new Type();
        type.setName(typeRequest.getName().toUpperCase());
        typeRepository.save(type);
    }

    private void checker(Optional<Type> type, String name) {
        if(type.isEmpty()) {
            throw new NotFoundException("Type with name: " + name + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
