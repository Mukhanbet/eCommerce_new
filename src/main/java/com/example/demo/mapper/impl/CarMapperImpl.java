package com.example.demo.mapper.impl;

import com.example.demo.dto.car.CarResponse;
import com.example.demo.entities.Car;
import com.example.demo.mapper.CarMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarMapperImpl implements CarMapper {
    @Override
    public CarResponse toDto(Car car) {
        CarResponse carResponse = new CarResponse();
        carResponse.setId(car.getId());
        carResponse.setName(car.getName());
        carResponse.setType(car.getType().getName());
        carResponse.setColor(car.getColor());
        carResponse.setYear(car.getYear());
        carResponse.setCountry(car.getCountry());
        carResponse.setPrice(car.getPrice());
        carResponse.setOwner(car.getSeller().getName());
        carResponse.setAvailable(car.isAvailable());
        carResponse.setAmount(car.getAmount());
        return carResponse;
    }

    @Override
    public List<CarResponse> toDtoS(List<Car> all) {
        List<CarResponse> carResponses = new ArrayList<>();
        for(Car car : all) {
            carResponses.add(toDto(car));
        }
        return carResponses;
    }
}
