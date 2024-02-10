package com.example.demo.service;

import com.example.demo.dto.car.CarRequest;
import com.example.demo.dto.car.CarResponse;

import java.util.List;

public interface CarService {
    List<CarResponse> getAll();
    List<CarResponse> getAllAvailableCars();
    List<CarResponse> getSolvedCars();
    List<CarResponse> getCarsSortedByPrice(String order);
    List<CarResponse> getByType(String type);
    List<String> compareWith(Long firstId, Long secondId);
    List<CarResponse> getSolvedCarsByType(String type);
    CarResponse findById(Long id);
    void updateById(Long id, CarRequest carRequest);
    void deleteById(Long id);
    void create(CarRequest carRequest, String sellerEmail);
}
