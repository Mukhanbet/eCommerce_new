package com.example.demo.service.impl;

import com.example.demo.dto.seller.SellerRequest;
import com.example.demo.dto.seller.SellerResponse;
import com.example.demo.entities.Seller;
import com.example.demo.entities.User;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.SellerMapper;
import com.example.demo.repositories.SellerRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    private final UserRepository userRepository;
    @Override
    public List<SellerResponse> getAll() {
        return sellerMapper.toDtoS(sellerRepository.findAll());
    }

    @Override
    public SellerResponse findByEmail(String email) {
        Optional<Seller> seller = sellerRepository.findByEmail(email);
        checker(seller, email);
        return sellerMapper.toDto(seller.get());
    }


    @Override
    public void updateByEmail(String email, SellerRequest sellerRequest) {
        Optional<Seller> seller = sellerRepository.findByEmail(email);
        checker(seller, email);
        if(userRepository.findByEmail(sellerRequest.getEmail()).isPresent() && !sellerRequest.getEmail().equals(email)) {
            throw new BadCredentialsException("User with email: " + sellerRequest.getEmail() + " have already exist!");
        }
        seller.get().setName(sellerRequest.getName());
        seller.get().setLastName(sellerRequest.getLastName());
        seller.get().setDateOfBirth(sellerRequest.getDateOfBirth());
        seller.get().setCardNumber(sellerRequest.getCardNumber());
        seller.get().setPhoneNumber(sellerRequest.getPhoneNumber());
        User user = seller.get().getUser();
        user.setEmail(sellerRequest.getEmail());
        user.setPassword(sellerRequest.getPassword());
        seller.get().setUser(user);
        sellerRepository.save(seller.get());
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<Seller> seller = sellerRepository.findByEmail(email);
        checker(seller, email);
        sellerRepository.deleteByEmail(email);
    }

    private void checker(Optional<Seller> manager, String email) {
        if(manager.isEmpty()) {
            throw new NotFoundException("Seller with email: " + email + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
