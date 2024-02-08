package com.example.demo.mapper.impl;

import com.example.demo.dto.type.TypeResponse;
import com.example.demo.entities.Type;
import com.example.demo.mapper.TypeMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeMapperImpl implements TypeMapper {
    @Override
    public TypeResponse toDto(Type type) {
        TypeResponse typeResponse = new TypeResponse();
        typeResponse.setId(type.getId());
        typeResponse.setName(type.getName());
        return typeResponse;
    }

    @Override
    public List<TypeResponse> toDtoS(List<Type> all) {
        List<TypeResponse> typeResponses = new ArrayList<>();
        for(Type type : all) {
            typeResponses.add(toDto(type));
        }
        return typeResponses;
    }
}
