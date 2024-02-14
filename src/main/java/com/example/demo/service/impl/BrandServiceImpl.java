package com.example.demo.service.impl;

import com.example.demo.dto.brand.BrandRequest;
import com.example.demo.dto.brand.BrandResponse;
import com.example.demo.entities.Brand;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.BrandMapper;
import com.example.demo.repositories.BrandRepository;
import com.example.demo.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    @Override
    public List<BrandResponse> getAll() {
        return brandMapper.toDtoS(brandRepository.findAll());
    }

    @Override
    public BrandResponse findByName(String name) {
        Optional<Brand> brand = brandRepository.findByName(name.toUpperCase());
        checker(brand, name);
        return brandMapper.toDto(brand.get());
    }

    @Override
    public void updateByName(String name, BrandRequest brandRequest) {
        Optional<Brand> brand = brandRepository.findByName(name.toUpperCase());
        checker(brand, name);
        if(brandRepository.findByName(brandRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Brand with name: " + brandRequest.getName() + " has already exist");
        }
        brand.get().setName(brandRequest.getName().toUpperCase());
        brand.get().setPlaceOfProduction(brandRequest.getPlaceOfProduction());
        brandRepository.deleteByName(name);
    }

    @Override
    public void deleteByName(String name) {
        Optional<Brand> brand = brandRepository.findByName(name.toUpperCase());
        checker(brand, name);
        brandRepository.deleteByName(name.toUpperCase());
    }

    @Override
    public void create(BrandRequest brandRequest) {
        Brand brand = new Brand();
        brand.setName(brandRequest.getName().toUpperCase());
        brand.setPlaceOfProduction(brandRequest.getPlaceOfProduction());
        brandRepository.save(brand);
    }

    private void checker(Optional<Brand> brand, String name) {
        if(brand.isEmpty()) {
            throw new NotFoundException("Brand with name: " + name + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
