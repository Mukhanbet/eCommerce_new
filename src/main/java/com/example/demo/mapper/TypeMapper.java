package com.example.demo.mapper;

import com.example.demo.dto.type.TypeResponse;
import com.example.demo.entities.Type;

import java.util.List;

public interface TypeMapper {
    TypeResponse toDto(Type type);
    List<TypeResponse> toDtoS(List<Type> all);

}
