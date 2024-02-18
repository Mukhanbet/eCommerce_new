package com.example.demo.service.impl;

import com.example.demo.dto.basket.BasketRequest;
import com.example.demo.dto.basket.BasketResponse;
import com.example.demo.entities.Basket;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.enums.Status;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.BasketMapper;
import com.example.demo.repositories.BasketRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.BasketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BasketMapper basketMapper;
    @Override
    public List<BasketResponse> getAll() {
        return basketMapper.toDtoS(basketRepository.findAll());
    }

    @Override
    public BasketResponse findById(Long id) {
        Optional<Basket> basket = basketRepository.findById(id);
        checker(basket, id);
        return basketMapper.toDto(basket.get());
    }

    @Override
    public void updateById(Long id, BasketRequest basketRequest) {
        Optional<Basket> basket = basketRepository.findById(id);
        checker(basket, id);
        if(basket.get().getEnrolProduct().getAmount() + basket.get().getAmount() < basketRequest.getAmount()) {
            throw new BadCredentialsException("The amount (" + basketRequest.getAmount() + ") of product exceeds than available");
        }
        int newQuantityInSystem = basket.get().getEnrolProduct().getAmount() + basket.get().getAmount() - basketRequest.getAmount();
        basket.get().setAmount(basketRequest.getAmount());
        double newSum = basket.get().getEnrolProduct().getPrice() * basketRequest.getAmount();
        basket.get().setTotalSum(newSum);

        Product product = basket.get().getEnrolProduct();
        product.setAmount(newQuantityInSystem);
        basket.get().setEnrolProduct(product);
        productRepository.save(product);
        basketRepository.save(basket.get());
    }

    @Override
    public void deleteById(Long id) {
        Optional<Basket> basket = basketRepository.findById(id);
        checker(basket, id);
        Product product = basket.get().getEnrolProduct();
        int newQuantityInSystem = product.getAmount() + basket.get().getAmount();
        product.setAmount(newQuantityInSystem); // todo is this correct?
        productRepository.save(product);
        basketRepository.deleteById(id);
    }

    @Override
    public void putProductToBasket(Long productId, String userEmail, BasketRequest basketRequest) {
        Basket basket = new Basket();
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()) {
            throw new NotFoundException("Product with id \"" + productId + "\" not found", HttpStatus.NOT_FOUND);
        }
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isEmpty()) {
            throw new NotFoundException("User with email \"" + userEmail + "\" not found", HttpStatus.NOT_FOUND);
        }
        if(basketRequest.getAmount() > product.get().getAmount()) {
            throw new BadCredentialsException("The amount of product exceeds than available");
        }
        basket.setAmount(basketRequest.getAmount());
        int newQuantityInSystem = product.get().getAmount() - basketRequest.getAmount();
        product.get().setAmount(newQuantityInSystem);
        double totalSum = product.get().getPrice() * basketRequest.getAmount();
        basket.setTotalSum(totalSum);
        basket.setCreatedDay(LocalDate.now());
//        basket.setEndDay(LocalDate.now().plusDays(7));
        basket.setEndDay(LocalDate.now().minusDays(1));
        basket.setEnrolProduct(product.get());
        basket.setEnrolUser(user.get());
        basketRepository.save(basket);
        productRepository.save(product.get());
    }

    @Scheduled(fixedRate = 100000)
    public void systemUpdates() {
        List<Basket> baskets = basketRepository.findAll();
        for(Basket basket : baskets) {
            if(basket.getEndDay().isBefore(LocalDate.now())) {
                Product product = basket.getEnrolProduct();
                product.setAmount(product.getAmount() + basket.getAmount());
                productRepository.save(product);
                basketRepository.deleteById(basket.getId());
            }
        }
    }

    private void checker(Optional<Basket> basket, Long id) {
        if(basket.isEmpty()) {
            throw new NotFoundException("In the basket there is no data with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
