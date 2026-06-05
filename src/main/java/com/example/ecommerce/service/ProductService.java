package com.example.ecommerce.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Product;

import com.example.ecommerce.entity.Category;

import com.example.ecommerce.repository.ProductRepository;

import com.example.ecommerce.repository.CategoryRepository;

import com.example.ecommerce.exception.ResourceNotFoundException;

import com.example.ecommerce.exception.BadRequestException;

import java.util.List;

@Service

@RequiredArgsConstructor

public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public Product create(Product product) {

        if (product.getCategory() != null) {

            Long catId = product.getCategory().getId();

            Category category = categoryRepository.findById(catId)

                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            product.setCategory(category);

        }

        return productRepository.save(product);

    }

    public List<Product> getAll() {

        return productRepository.findAll();

    }

    public Product getById(Long id) {

        return productRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    }

    public Product update(Long id, Product updated) {

        Product product = getById(id);

        product.setName(updated.getName());

        product.setDescription(updated.getDescription());

        product.setPrice(updated.getPrice());

        product.setStockQuantity(updated.getStockQuantity());

        if (updated.getCategory() != null) {

            Long catId = updated.getCategory().getId();

            Category category = categoryRepository.findById(catId)

                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            product.setCategory(category);

        }

        return productRepository.save(product);

    }

    public void delete(Long id) {

        Product product = getById(id);

        productRepository.delete(product);

    }

    // Méthode utile pour OrderService

    public void reduceStock(Product product, int quantity) {

        if (product.getStockQuantity() < quantity) {

            throw new BadRequestException("Insufficient stock for product: " + product.getName());

        }

        product.setStockQuantity(product.getStockQuantity() - quantity);

        productRepository.save(product);

    }

}
