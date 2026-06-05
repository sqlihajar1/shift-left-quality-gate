package com.example.ecommerce.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.entity.Customer;

import com.example.ecommerce.service.CustomerService;

import jakarta.validation.Valid;

import java.util.List;

@RestController

@RequestMapping("/customers")

@RequiredArgsConstructor

public class CustomerController {

    private final CustomerService customerService;

    @PostMapping

    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {

        return ResponseEntity.status(201).body(customerService.create(customer));

    }

    @GetMapping

    public ResponseEntity<List<Customer>> getAll() {

        return ResponseEntity.status(200).body(customerService.getAll());

    }

    @GetMapping("/{id}")

    public ResponseEntity<Customer> getById(@PathVariable Long id) {

        return ResponseEntity.status(200).body(customerService.getById(id));

    }

    @PutMapping("/{id}")

    public ResponseEntity<Customer> update(@PathVariable Long id, @Valid @RequestBody Customer customer) {

        return ResponseEntity.status(200).body(customerService.update(id, customer));

    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> delete(@PathVariable Long id) {

        customerService.delete(id);

        return ResponseEntity.status(204).build();

    }

}
