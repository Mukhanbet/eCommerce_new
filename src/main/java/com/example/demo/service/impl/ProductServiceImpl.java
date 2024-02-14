package com.example.demo.service.impl;

import com.example.demo.dto.product.ProductRequest;
import com.example.demo.dto.product.ProductResponse;
import com.example.demo.entities.Product;
import com.example.demo.entities.Manager;
import com.example.demo.entities.Type;
import com.example.demo.entities.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.ManagerRepository;
import com.example.demo.repositories.TypeRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final TypeRepository typeRepository;
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;

    // todo add the code which will subtract the amount of product when the person will buy it or put the basket
    @Override
    public List<ProductResponse> getAll() {
        return productMapper.toDtoS(productRepository.findAll());
    }

    @Override
    public List<ProductResponse> getAllAvailableProducts() {
        List<Product> availableProducts = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.isAvailable()) {
                availableProducts.add(product);
            }
        }
        return productMapper.toDtoS(availableProducts);
    }

    @Override
    public List<ProductResponse> getSolvedProducts() {
        List<Product> solvedProducts = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(!product.isAvailable()) {
                solvedProducts.add(product);
            }
        }
        return productMapper.toDtoS(solvedProducts);
    }

    @Override
    public List<ProductResponse> getProductsSortedByPrice(String order) {
        List<Product> productsByKillingPrice = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.isAvailable()) {
                productsByKillingPrice.add(product);
            }
        }
        if(order.equalsIgnoreCase("increasing")) {
            Collections.sort(productsByKillingPrice, (product1, product2) -> Double.compare(product2.getPrice(), product1.getPrice()));
        } else if (order.equalsIgnoreCase("killing")) {
            Collections.sort(productsByKillingPrice, Comparator.comparingDouble(Product::getPrice));
        }
        return productMapper.toDtoS(productsByKillingPrice);
    }

    @Override
    public List<ProductResponse> getByType(String type) {
        List<Product> Products = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.getType().getName().equals(type.toUpperCase()) && product.isAvailable()) {
                Products.add(product);
            }
        }
        return productMapper.toDtoS(Products);
    }

    @Override
    public List<String> compareWith(Long firstId, Long secondId) {
        Optional<Product> firstproduct = productRepository.findById(firstId);
        checker(firstproduct, firstId);
        Optional<Product> secondproduct = productRepository.findById(secondId);
        checker(secondproduct, secondId);
        List<String> difference = new ArrayList<>();
        difference.add("Name:    " + firstproduct.get().getName() + "     " + secondproduct.get().getName());
        difference.add("Color:   " + firstproduct.get().getColor() + "    " + secondproduct.get().getColor());
        difference.add("Year:     " + firstproduct.get().getYear() + "     " + secondproduct.get().getYear());
        difference.add("Country:  " + firstproduct.get().getCountry() + "  " + secondproduct.get().getCountry());
        difference.add("Price:    " + firstproduct.get().getPrice() + "    " + secondproduct.get().getPrice());
        return difference;
    }

    @Override
    public List<ProductResponse> getSolvedProductsByType(String type) {
        List<Product> solvedproductsSameType = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.getType().getName().equals(type.toUpperCase()) && !product.isAvailable()) {
                solvedproductsSameType.add(product);
            }
        }
        return productMapper.toDtoS(solvedproductsSameType);
    }

    @Override
    public ProductResponse findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        checker(product, id);
        return productMapper.toDto(product.get());
    }

    @Override
    public void putTheProductToBasket(Long productId, String userEmail) {
        Optional<Product> product = productRepository.findById(productId);
        checker(product, productId);
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isEmpty()) {
            throw new NotFoundException("User with email: " + userEmail, HttpStatus.NOT_FOUND);
        }
        List<User> enrolUser = product.get().getEnrolUsersToProducts();
        enrolUser.add(user.get());
        product.get().setEnrolUsersToProducts(enrolUser);
        productRepository.save(product.get());
    }

    @Override
    public void updateById(Long id, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(id);
        checker(product, id);
        product.get().setName(productRequest.getName());
        product.get().setColor(productRequest.getColor());
        product.get().setYear(productRequest.getYear());
        product.get().setCountry(productRequest.getCountry());
        product.get().setPrice(productRequest.getPrice());
        product.get().setAvailable(productRequest.isAvailable());
        product.get().setAmount(productRequest.getAmount());

        Optional<Type> type = typeRepository.findByName(productRequest.getType().toUpperCase());
        if(type.isEmpty()) {
            throw new NotFoundException("In the system this type: " + productRequest.getType() + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        product.get().setType(type.get());
        productRepository.save(product.get());
    }

    @Override
    public void deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        checker(product, id);
        productRepository.deleteById(id);
    }

    @Override
    public void create(ProductRequest productRequest, String managerEmail) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setColor(productRequest.getColor());
        product.setYear(productRequest.getYear());
        product.setCountry(productRequest.getCountry());
        product.setPrice(productRequest.getPrice());
        product.setAvailable(productRequest.isAvailable());
        product.setAmount(productRequest.getAmount());
        Optional<Type> type = typeRepository.findByName(productRequest.getType().toUpperCase());
        if(type.isEmpty()) {
            throw new NotFoundException("In the system this type: " + productRequest.getType() + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        product.setType(type.get());

        Optional<Manager> manager = managerRepository.findByEmail(managerEmail);
        if(manager.isEmpty()) {
            throw new NotFoundException("Manager with email: " + managerEmail + " not found", HttpStatus.NOT_FOUND);
        }
        product.setManager(manager.get());
        productRepository.save(product);
    }

    private void checker(Optional<Product> product, Long id) {
        if(product.isEmpty()) {
            throw new NotFoundException("product with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }


}
