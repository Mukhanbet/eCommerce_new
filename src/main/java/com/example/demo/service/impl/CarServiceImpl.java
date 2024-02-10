package com.example.demo.service.impl;

import com.example.demo.dto.car.CarRequest;
import com.example.demo.dto.car.CarResponse;
import com.example.demo.entities.Car;
import com.example.demo.entities.Seller;
import com.example.demo.entities.Type;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.CarMapper;
import com.example.demo.repositories.CarRepository;
import com.example.demo.repositories.SellerRepository;
import com.example.demo.repositories.TypeRepository;
import com.example.demo.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final TypeRepository typeRepository;
    private final SellerRepository sellerRepository;

    // todo add the filter where will display the cars ordered by type
    // todo add the filter where will display the cars by created day;
    @Override
    public List<CarResponse> getAll() {
        return carMapper.toDtoS(carRepository.findAll());
    }

    @Override
    public List<CarResponse> getAllAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for(Car car : carRepository.findAll()) {
            if(car.isAvailable()) {
                availableCars.add(car);
            }
        }
        return carMapper.toDtoS(availableCars);
    }

    @Override
    public List<CarResponse> getSolvedCars() {
        List<Car> solvedCars = new ArrayList<>();
        for(Car car : carRepository.findAll()) {
            if(!car.isAvailable()) {
                solvedCars.add(car);
            }
        }
        return carMapper.toDtoS(solvedCars);
    }

    @Override
    public List<CarResponse> getCarsSortedByPrice(String order) {
        List<Car> carsByKillingPrice = new ArrayList<>();
        for(Car car : carRepository.findAll()) {
            if(car.isAvailable()) {
                carsByKillingPrice.add(car);
            }
        }
        if(order.equalsIgnoreCase("increasing")) {
            Collections.sort(carsByKillingPrice, (car1, car2) -> Double.compare(car2.getPrice(), car1.getPrice()));
        } else if (order.equalsIgnoreCase("killing")) {
            Collections.sort(carsByKillingPrice, Comparator.comparingDouble(Car::getPrice));
        }
        return carMapper.toDtoS(carsByKillingPrice);
    }

    @Override
    public List<CarResponse> getByType(String type) {
        List<Car> cars = new ArrayList<>();
        for(Car car : carRepository.findAll()) {
            if(car.getType().getName().equals(type.toUpperCase()) && car.isAvailable()) {
                cars.add(car);
            }
        }
        return carMapper.toDtoS(cars);
    }

    @Override
    public List<String> compareWith(Long firstId, Long secondId) {
        Optional<Car> firstCar = carRepository.findById(firstId);
        checker(firstCar, firstId);
        Optional<Car> secondCar = carRepository.findById(secondId);
        checker(secondCar, secondId);
        List<String> difference = new ArrayList<>();
        difference.add("Name:    " + firstCar.get().getName() + "     " + secondCar.get().getName());
        difference.add("Color:   " + firstCar.get().getColor() + "    " + secondCar.get().getColor());
        difference.add("Year:     " + firstCar.get().getYear() + "     " + secondCar.get().getYear());
        difference.add("Country:  " + firstCar.get().getCountry() + "  " + secondCar.get().getCountry());
        difference.add("Price:    " + firstCar.get().getPrice() + "    " + secondCar.get().getPrice());
        return difference;
    }

    @Override
    public List<CarResponse> getSolvedCarsByType(String type) {
        List<Car> solvedCarsSameType = new ArrayList<>();
        for(Car car : carRepository.findAll()) {
            if(car.getType().getName().equals(type.toUpperCase()) && !car.isAvailable()) {
                solvedCarsSameType.add(car);
            }
        }
        return carMapper.toDtoS(solvedCarsSameType);
    }

    @Override
    public CarResponse findById(Long id) {
        Optional<Car> car = carRepository.findById(id);
        checker(car, id);
        return carMapper.toDto(car.get());
    }

    @Override
    public void updateById(Long id, CarRequest carRequest) {
        Optional<Car> car = carRepository.findById(id);
        checker(car, id);
        car.get().setName(carRequest.getName());
        car.get().setColor(carRequest.getColor());
        car.get().setYear(carRequest.getYear());
        car.get().setCountry(carRequest.getCountry());
        car.get().setPrice(carRequest.getPrice());
        car.get().setAvailable(carRequest.isAvailable());
        car.get().setAmount(carRequest.getAmount());

        Optional<Type> type = typeRepository.findByName(carRequest.getType().toUpperCase());
        if(type.isEmpty()) {
            throw new NotFoundException("In the system this type: " + carRequest.getType() + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        car.get().setType(type.get());
        carRepository.save(car.get());
    }

    @Override
    public void deleteById(Long id) {
        Optional<Car> car = carRepository.findById(id);
        checker(car, id);
        carRepository.deleteById(id);
    }

    @Override
    public void create(CarRequest carRequest, String sellerEmail) {
        Car car = new Car();
        car.setName(carRequest.getName());
        car.setColor(carRequest.getColor());
        car.setYear(carRequest.getYear());
        car.setCountry(carRequest.getCountry());
        car.setPrice(carRequest.getPrice());
        car.setAvailable(carRequest.isAvailable());
        car.setAmount(carRequest.getAmount());
        Optional<Type> type = typeRepository.findByName(carRequest.getType().toUpperCase());
        if(type.isEmpty()) {
            throw new NotFoundException("In the system this type: " + carRequest.getType() + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        car.setType(type.get());

        Optional<Seller> seller = sellerRepository.findByEmail(sellerEmail);
        if(seller.isEmpty()) {
            throw new NotFoundException("Seller with email: " + sellerEmail + " not found", HttpStatus.NOT_FOUND);
        }
        car.setSeller(seller.get());
        carRepository.save(car);
    }

    private void checker(Optional<Car> car, Long id) {
        if(car.isEmpty()) {
            throw new NotFoundException("Car with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
