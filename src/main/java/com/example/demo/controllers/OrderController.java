package com.example.demo.controllers;

import com.example.demo.dto.order.OrderRequest;
import com.example.demo.dto.order.OrderResponse;
import com.example.demo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/getAll")
    public List<OrderResponse> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/getUserOrders/{userEmail}")
    public List<OrderResponse> getUserOrders(@PathVariable String userEmail) {
        return orderService.getUserOrders(userEmail);
    }

    @GetMapping("/getOrdersByStatus/{status}")
    public List<OrderResponse> getOrdersByStatus(@PathVariable String status) {
        return orderService.getOrdersByStatus(status);
    }

    @GetMapping("/getOrdersByPayment/{payment}")
    public List<OrderResponse> getOrdersByPayment(@PathVariable String payment) {
        return orderService.getOrdersByPayment(payment);
    }

    @GetMapping("/getOrdersByDate/{date}")
    public List<OrderResponse> getOrdersByDate(@PathVariable LocalDate date) {
        return orderService.getOrdersByDate(date);
    }

    @GetMapping("/getOrdersByProductId/{productId}")
    public List<OrderResponse> getOrdersByProduct(@PathVariable Long productId) {
        return orderService.getOrdersByProduct(productId);
    }

    @GetMapping("/findById/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PutMapping("/updateById/{id}")
    public void updateById(@RequestBody OrderRequest orderRequest, @PathVariable Long id) {
        orderService.updateById(orderRequest, id);
    }

    @PatchMapping("/updateByField/{id}")
    public void updateByField(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        orderService.updateByField(id, fields);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Long id) {
        orderService.deleteById(id);
    }

    @PostMapping("/orderOperation/user/{userEmail}/product/{productId}")
    public void orderOperation(@PathVariable String userEmail, @PathVariable Long productId, @RequestBody OrderRequest orderRequest) {
        orderService.orderOperation(userEmail, productId, orderRequest);
    }
}
