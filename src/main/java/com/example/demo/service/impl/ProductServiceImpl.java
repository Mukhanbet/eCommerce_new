package com.example.demo.service.impl;

import com.example.demo.dto.product.ProductRequest;
import com.example.demo.dto.product.ProductResponse;
import com.example.demo.entities.*;
import com.example.demo.enums.StatusDiscount;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repositories.*;
import com.example.demo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final DiscountRepository discountRepository;

    // todo add the code which will subtract the amount of product when the person will buy it or put the basket
    // todo write the function where will display product by popularity in solved
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
        List<Product> productsBySortedPrice = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.isAvailable()) {
                productsBySortedPrice.add(product);
            }
        }
        if(order.equalsIgnoreCase("increasing")) {
            Collections.sort(productsBySortedPrice, (product1, product2) -> Double.compare(product2.getPrice(), product1.getPrice()));
        } else if (order.equalsIgnoreCase("killing")) {
            Collections.sort(productsBySortedPrice, Comparator.comparingDouble(Product::getPrice));
        }
        return productMapper.toDtoS(productsBySortedPrice);
    }

    @Override
    public List<ProductResponse> getPopularProducts() {
        List<Product> sortedProducts = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.isAvailable()) {
                sortedProducts.add(product);
            }
        }
        Collections.sort(sortedProducts,  (product1, product2) -> Double.compare(product2.getRating(), product1.getRating()));
        return productMapper.toDtoS(sortedProducts);
    }

    @Override
    public List<ProductResponse> getByCategory(String category) {
        List<Product> sortedProductsByCategory = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.getCategory().getName().equals(category.toUpperCase()) && product.isAvailable()) {
                sortedProductsByCategory.add(product);
            }
        }
        return productMapper.toDtoS(sortedProductsByCategory);
    }

    @Override
    public List<ProductResponse> getByBrand(String brand) {
        List<Product> sortedProductByBrand = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.getBrand().getName().equalsIgnoreCase(brand.toUpperCase()) && product.isAvailable()) {
                sortedProductByBrand.add(product);
            }
        }
        return productMapper.toDtoS(sortedProductByBrand);
    }

    @Override
    public List<String> compareWith(Long firstId, Long secondId) {
        Optional<Product> firstProduct = productRepository.findById(firstId);
        checker(firstProduct, firstId);
        Optional<Product> secondProduct = productRepository.findById(secondId);
        checker(secondProduct, secondId);
        List<String> difference = new ArrayList<>();
        //todo write here the compare functions
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
    public void updateById(Long id, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(id);
        checker(product, id);
        product.get().setName(productRequest.getName());
        product.get().setDescription(productRequest.getDescription());
        product.get().setPrice(productRequest.getPrice());
        product.get().setAmount(productRequest.getAmount());
        product.get().setSize(productRequest.getSize());
        product.get().setColor(productRequest.getColor());
        product.get().setAvailable(productRequest.isAvailable());
        Optional<Category> category = categoryRepository.findByName(productRequest.getCategory().toUpperCase());
        if(category.isEmpty()) {
            throw new NotFoundException("In the system this category: " + productRequest.getCategory() + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        product.get().setCategory(category.get());
        Optional<Brand> brand = brandRepository.findByName(productRequest.getBrand().toUpperCase());
        if(brand.isEmpty()) {
            throw new NotFoundException("In the system this brand: " + productRequest.getBrand() + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        product.get().setBrand(brand.get());
        productRepository.save(product.get());
    }

    @Override
    public void giveDiscount(Long productId, Long discountId) {
        Optional<Product> product = productRepository.findById(productId);
        checker(product, productId);
        Optional<Discount> discount = discountRepository.findById(discountId);
        if(discount.isEmpty()) {
            throw new NotFoundException("Discount with id: " + discountId + " not found", HttpStatus.NOT_FOUND);
        }
        product.get().setDiscount(discount.get());
        double discountPrice = generateDiscountPrice(product.get().getPrice(), discount.get().getDiscount());
        product.get().setDiscountPrice(discountPrice);
        productRepository.save(product.get());
    }

    @Override
    public void deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        checker(product, id);
        productRepository.deleteById(id);
    }

    @Override
    public void create(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setAmount(productRequest.getAmount());
        product.setSize(productRequest.getSize());
        product.setColor(productRequest.getColor());
        product.setAvailable(productRequest.isAvailable());
        Optional<Category> category = categoryRepository.findByName(productRequest.getCategory().toUpperCase());
        if(category.isEmpty()) {
            throw new NotFoundException("In the system this category: " + productRequest.getCategory() + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        product.setCategory(category.get());

        Optional<Brand> brand = brandRepository.findByName(productRequest.getBrand().toUpperCase());
        if(brand.isEmpty()) {
            throw new NotFoundException("In the system this brand: " + productRequest.getBrand() + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        product.setBrand(brand.get());
        product.setAddedDate(LocalDate.now());
        productRepository.save(product);
    }

    @Scheduled(fixedRate = 10000)
    public void systemUpdate() {
        for (Product product : productRepository.findAll()){
            if (product.getDiscount()!=null){
                if (product.getDiscount().getStatus().equals(StatusDiscount.INACTIVE)){
                    product.setDiscount(null);
                    product.setDiscountPrice(0);
                    productRepository.save(product);
                }
            }
        }
    }

    private void checker(Optional<Product> product, Long id) {
        if(product.isEmpty()) {
            throw new NotFoundException("product with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    private double generateDiscountPrice(double price, int discount) {
        double newPrice = (price * discount) / 100;
        return price - newPrice;
    }
}
