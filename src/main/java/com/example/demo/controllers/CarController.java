package com.example.demo.controllers;

import com.example.demo.dto.car.CarRequest;
import com.example.demo.dto.car.CarResponse;
import com.example.demo.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    @GetMapping("/getAll")
    public List<CarResponse> getAll() {
        return carService.getAll();
    }

    @GetMapping("/getByType/{type}")
    public List<CarResponse> getByType(@PathVariable String type) {
        return carService.getByType(type);
    }

    @GetMapping("/findById/{id}")
    public CarResponse findById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @PutMapping("/updateById/{id}")
    public void updateById(@PathVariable Long id, @RequestBody CarRequest carRequest) {
        carService.updateById(id, carRequest);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Long id) {
        carService.deleteById(id);
    }

    @PostMapping("/create/{sellerEmail}")
    public void create(@RequestBody CarRequest carRequest, @PathVariable String sellerEmail) {
        carService.create(carRequest, sellerEmail);
    }
}
