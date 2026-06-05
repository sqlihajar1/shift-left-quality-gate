package com.example.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderStatus;
import com.example.ecommerce.service.OrderService;
import java.util.List;
import com.example.ecommerce.dto.OrderDTO;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.status(201).body(orderService.create(orderDTO));
    }
    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.status(200).body(orderService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(orderService.getById(id));
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id,
                                              @RequestBody Map<String, String> body) {
        String statusStr = body.get("status");
        OrderStatus status = OrderStatus.valueOf(statusStr.toUpperCase());
        return ResponseEntity.status(200).body(orderService.updateStatus(id, status));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.status(204).build();
    }
}
