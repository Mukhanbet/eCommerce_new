package com.example.demo.service.impl;

import com.example.demo.dto.product.ProductRequest;
import com.example.demo.dto.product.ProductResponse;
import com.example.demo.entities.Product;
import com.example.demo.entities.Manager;
import com.example.demo.entities.Category;
import com.example.demo.entities.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.ManagerRepository;
import com.example.demo.repositories.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
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
    public List<ProductResponse> getByCategory(String category) {
        List<Product> Products = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.getCategory().getName().equals(category.toUpperCase()) && product.isAvailable()) {
                Products.add(product);
            }
        }
        return productMapper.toDtoS(Products);
    }

    @Override
    public List<String> compareWith(Long firstId, Long secondId) {
        Optional<Product> firstProduct = productRepository.findById(firstId);
        checker(firstProduct, firstId);
        Optional<Product> secondProduct = productRepository.findById(secondId);
        checker(secondProduct, secondId);
        List<String> difference = new ArrayList<>();
        difference.add("Name:    " + firstProduct.get().getName() + "     " + secondProduct.get().getName());
        difference.add("Color:   " + firstProduct.get().getColor() + "    " + secondProduct.get().getColor());
        difference.add("Year:     " + firstProduct.get().getYear() + "     " + secondProduct.get().getYear());
        difference.add("Country:  " + firstProduct.get().getCountry() + "  " + secondProduct.get().getCountry());
        difference.add("Price:    " + firstProduct.get().getPrice() + "    " + secondProduct.get().getPrice());
        return difference;
    }

    @Override
    public List<ProductResponse> getSolvedProductsByCategory(String category) {
        List<Product> solvedProductsSameCategory = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.getCategory().getName().equals(category.toUpperCase()) && !product.isAvailable()) {
                solvedProductsSameCategory.add(product);
            }
        }
        return productMapper.toDtoS(solvedProductsSameCategory);
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

        Optional<Category> category = categoryRepository.findByName(productRequest.getCategory().toUpperCase());
        if(category.isEmpty()) {
            throw new NotFoundException("In the system this category: " + productRequest.getCategory() + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        product.get().setCategory(category.get());
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
        Optional<Category> category = categoryRepository.findByName(productRequest.getCategory().toUpperCase());
        if(category.isEmpty()) {
            throw new NotFoundException("In the system this category: " + productRequest.getCategory() + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        product.setCategory(category.get());

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
