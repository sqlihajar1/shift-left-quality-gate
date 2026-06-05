package com.example.ecommerce.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Customer;

import com.example.ecommerce.repository.CustomerRepository;

import com.example.ecommerce.exception.ResourceNotFoundException;

import com.example.ecommerce.exception.BadRequestException;

import java.util.List;

@Service

@RequiredArgsConstructor

public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer create(Customer customer) {

        if (customerRepository.existsByEmail(customer.getEmail())) {

            throw new BadRequestException("Email already exists");

        }

        return customerRepository.save(customer);

    }

    public List<Customer> getAll() {

        return customerRepository.findAll();

    }

    public Customer getById(Long id) {

        return customerRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

    }

    public Customer update(Long id, Customer updated) {

        Customer customer = getById(id);

        customer.setFirstName(updated.getFirstName());

        customer.setLastName(updated.getLastName());

        if (!customer.getEmail().equals(updated.getEmail()) &&

                customerRepository.existsByEmail(updated.getEmail())) {

            throw new BadRequestException("Email already exists");

        }

        customer.setEmail(updated.getEmail());

        customer.setPhone(updated.getPhone());

        return customerRepository.save(customer);

    }

    public void delete(Long id) {

        Customer customer = getById(id);

        customerRepository.delete(customer);

    }

}