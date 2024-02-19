package com.example.demo.service.impl;

import com.example.demo.dto.discount.DiscountRequest;
import com.example.demo.dto.discount.DiscountResponse;
import com.example.demo.entities.Discount;
import com.example.demo.entities.Product;
import com.example.demo.enums.StatusDiscount;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.DiscountMapper;
import com.example.demo.repositories.DiscountRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final ProductRepository productRepository;
    @Override
    public List<DiscountResponse> getAll() {
        return discountMapper.toDtoS(discountRepository.findAll());
    }

    @Override
    public DiscountResponse findById(Long id) {
        Optional<Discount> discount = discountRepository.findById(id);
        checker(discount, id);
        return discountMapper.toDto(discount.get());
    }

    @Override
    public void updateById(Long id, DiscountRequest discountRequest) {
        Optional<Discount> discount = discountRepository.findById(id);
        checker(discount, id);
        discount.get().setName(discountRequest.getName());
        discount.get().setDiscount(discountRequest.getDiscount());
        discount.get().setStartDate(discountRequest.getStartDate());
        discount.get().setEndDate(discountRequest.getEndDate());
        discount.get().setStatus(StatusDiscount.valueOf(discountRequest.getStatus()));
        discountRepository.save(discount.get());
        // todo write product's function
    }

    @Override
    public void updateByFields(Long id, Map<String, Object> fields) {
        Optional<Discount> discount = discountRepository.findById(id);
        checker(discount, id);
        fields.forEach((key, value) -> {
            if(key.equals("name")) {
                discount.get().setName((String) value);
            }
            if(key.equals("discount")) {
                discount.get().setDiscount((int) value);
            }
            if(key.equals("startDate")) {
                discount.get().setStartDate((LocalDate) value);
            }
            if(key.equals("endDate")) {
                discount.get().setEndDate((LocalDate) value);
            }
            if(key.equals("status")) {
                discount.get().setStatus(StatusDiscount.valueOf((String) value));
            }
            discountRepository.save(discount.get());
        });
    }

    @Override
    public void deleteById(Long id) {
        Optional<Discount> discount = discountRepository.findById(id);
        checker(discount, id);
        discountRepository.deleteById(id);
    }

    @Override
    public void create(DiscountRequest discountRequest) {
        Discount discount = new Discount();
        discount.setName(discountRequest.getName());
        discount.setDiscount(discountRequest.getDiscount());
        discount.setStartDate(discountRequest.getStartDate());
        discount.setEndDate(discountRequest.getEndDate());
        discount.setStatus(StatusDiscount.valueOf(discountRequest.getStatus()));
        discountRepository.save(discount);
    }
    private void checker(Optional<Discount> discount, Long id) {
        if(discount.isEmpty()) {
            throw new NotFoundException("Discount with id \"" + id + "\" not found", HttpStatus.NOT_FOUND);
        }
    }
}
