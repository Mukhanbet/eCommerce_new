package com.example.demo.mapper;

import com.example.demo.dto.car.CarResponse;
import com.example.demo.entities.Car;

import java.util.List;

public interface CarMapper {
    CarResponse toDto(Car car);
    List<CarResponse> toDtoS(List<Car> all);
}
