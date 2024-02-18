package com.example.demo.service.impl;

import com.example.demo.dto.order.OrderRequest;
import com.example.demo.dto.order.OrderResponse;
import com.example.demo.entities.Order;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.enums.Payment;
import com.example.demo.enums.Status;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderResponse> getAll() {
        return orderMapper.toDtoS(orderRepository.findAll());
    }

    @Override
    public List<OrderResponse> getUserOrders(String userEmail) {
        List<Order> userOrders = new ArrayList<>();
        for(Order order : orderRepository.findAll()) {
            if(order.getEnrolUserToOrder().getEmail().equals(userEmail)) {
                userOrders.add(order);
            }
        }
        return orderMapper.toDtoS(userOrders);
    }

    @Override
    public List<OrderResponse> getOrdersByStatus(String status) {
        List<Order> ordersByStatus = new ArrayList<>();
        for(Order order : orderRepository.findAll()) {
            if(order.getStatus().equals(Status.valueOf(status))) {
                ordersByStatus.add(order);
            }
        }
        return orderMapper.toDtoS(ordersByStatus);
    }

    @Override
    public List<OrderResponse> getOrdersByPayment(String payment) {
        List<Order> ordersByPayment = new ArrayList<>();
        for(Order order : orderRepository.findAll()) {
            if(order.getPayment().equals(Payment.valueOf(payment))) {
                ordersByPayment.add(order);
            }
        }
        return orderMapper.toDtoS(ordersByPayment);
    }

    @Override
    public List<OrderResponse> getOrdersByDate(LocalDate date) {
        List<Order> ordersByDate = new ArrayList<>();
        for(Order order : orderRepository.findAll()) {
            if(order.getDateOfOrder().equals(date)) {
                ordersByDate.add(order);
            }
        }
        return orderMapper.toDtoS(ordersByDate);
    }

    @Override
    public List<OrderResponse> getOrdersByProduct(Long productId) {
        List<Order> ordersByProduct = new ArrayList<>();
        for(Order order : orderRepository.findAll()) {
            if(order.getEnrolProductToOrder().getId().equals(productId)) {
                ordersByProduct.add(order);
            }
        }
        return orderMapper.toDtoS(ordersByProduct);
    }

    @Override
    public OrderResponse findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        checker(order, id);
        return orderMapper.toDto(order.get());
    }

    @Override
    public void updateById(OrderRequest orderRequest, Long id) {
        Optional<Order> order = orderRepository.findById(id);
        checker(order, id);
        if(order.get().getEnrolProductToOrder().getAmount() + order.get().getAmount() < orderRequest.getAmount()) {
            throw new BadCredentialsException("The amount of product exceeds than available");
        }
        if(!order.get().getStatus().equals(Status.ACTIVE)) {
            throw new BadCredentialsException("You can't update the order at the moment!");
        }
        int newQuantityInSystem = order.get().getEnrolProductToOrder().getAmount() + order.get().getAmount() - orderRequest.getAmount();
        order.get().setAmount(orderRequest.getAmount());
        order.get().setAddress(orderRequest.getAddress());
        order.get().setPayment(Payment.valueOf(orderRequest.getPayment()));
        double newSum = orderRequest.getAmount() * order.get().getEnrolProductToOrder().getPrice();
        order.get().setSum(newSum);

        Product product = order.get().getEnrolProductToOrder();
        product.setAmount(newQuantityInSystem);
        order.get().setEnrolProductToOrder(product);
        productRepository.save(product);
        orderRepository.save(order.get());
    }

    @Override
    public void updateByField(Long id, Map<String, Object> fields) {
        Optional<Order> order = orderRepository.findById(id);
        checker(order, id);
        fields.forEach((key, value) -> {
//            Field field = ReflectionUtils.findField(Order.class, key);
//            field.setAccessible(true);
//            ReflectionUtils.setField(field, order, value);
            if(key.equals("status")) {
                order.get().setStatus(Status.valueOf((String) value));
            }
        });
        orderRepository.save(order.get());
        // todo here we have to check again which fields will updated
    }

    @Override
    public void deleteById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        checker(order, id);
        Product product = order.get().getEnrolProductToOrder();
        int newQuantityInSystem = order.get().getAmount() + product.getAmount();
        product.setAmount(newQuantityInSystem);
        productRepository.save(product);
        orderRepository.deleteById(id);
    }

    @Override
    public void orderOperation(String userEmail, Long productId, OrderRequest orderRequest) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isEmpty()) {
            throw new NotFoundException("User with email \"" + userEmail + "\" not found", HttpStatus.NOT_FOUND);
        }

        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()) {
            throw new NotFoundException("Product with id \"" + productId + "\" not found", HttpStatus.NOT_FOUND);
        }

        if(orderRequest.getAmount() > product.get().getAmount()) {
            throw new BadCredentialsException("The amount of product exceeds than available");
        }
        int newQuantityInSystem = product.get().getAmount() - orderRequest.getAmount();
        product.get().setAmount(newQuantityInSystem);
        Order order = new Order();
        order.setAmount(orderRequest.getAmount());
        order.setAddress(orderRequest.getAddress());
        order.setPayment(Payment.valueOf(orderRequest.getPayment()));
        double sum = product.get().getPrice() * orderRequest.getAmount();
        order.setSum(sum);
        order.setDateOfOrder(LocalDate.now());
        order.setStatus(Status.ACTIVE);
        order.setEnrolUserToOrder(user.get());
        order.setEnrolProductToOrder(product.get());
        orderRepository.save(order);
        productRepository.save(product.get());
    }

    @Scheduled(fixedRate = 100000)
    public void systemUpdates() {
        for(Order order : orderRepository.findAll()) {
            if(order.getStatus().equals(Status.RETURNED)) {
                Product product = order.getEnrolProductToOrder();
                int newQuantityInSystem = product.getAmount() + order.getAmount();
                product.setAmount(newQuantityInSystem);
                productRepository.save(product);
            }
        }
    }

    private void checker(Optional<Order> order, Long id) {
        if(order.isEmpty()) {
            throw new NotFoundException("Order with id: \"" + id + "\" not found", HttpStatus.NOT_FOUND);
        }
    }
}
