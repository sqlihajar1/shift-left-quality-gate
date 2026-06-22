package com.example.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.ecommerce.dto.CustomerDTO;
import com.example.ecommerce.dto.EntityMapper;
import com.example.ecommerce.entity.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.exception.BadRequestException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDTO create(CustomerDTO customerDTO) {
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        Customer customer = EntityMapper.toEntity(customerDTO);
        return EntityMapper.toDTO(customerRepository.save(customer));
    }

    public List<CustomerDTO> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(EntityMapper::toDTO)
                .toList();
    }

    public CustomerDTO getById(Long id) {
        return EntityMapper.toDTO(findById(id));
    }

    public CustomerDTO update(Long id, CustomerDTO customerDTO) {
        Customer customer = findById(id);
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        if (!customer.getEmail().equals(customerDTO.getEmail()) &&
                customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        return EntityMapper.toDTO(customerRepository.save(customer));
    }

    public void delete(Long id) {
        customerRepository.delete(findById(id));
    }

    // Méthode interne — retourne l'entité pour OrderService
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }
}