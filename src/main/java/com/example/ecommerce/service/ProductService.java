package com.example.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.EntityMapper;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
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

    public ProductDTO create(ProductDTO productDTO) {
        Product product = EntityMapper.toEntity(productDTO);
        // ✅ Supporte category: {id} ET categoryId
        Long catId = productDTO.getEffectiveCategoryId();
        if (catId != null) {
            Category category = categoryRepository.findById(catId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            product.setCategory(category);
        }
        return EntityMapper.toDTO(productRepository.save(product));
    }

    public List<ProductDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(EntityMapper::toDTO)
                .toList();
    }

    public ProductDTO getById(Long id) {
        return EntityMapper.toDTO(findById(id));
    }

    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product product = findById(id);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        // ✅ Supporte category: {id} ET categoryId
        Long catId = productDTO.getEffectiveCategoryId();
        if (catId != null) {
            Category category = categoryRepository.findById(catId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            product.setCategory(category);
        }
        return EntityMapper.toDTO(productRepository.save(product));
    }

    public void delete(Long id) {
        productRepository.delete(findById(id));
    }

    // Méthode interne — retourne l'entité pour OrderService
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public void reduceStock(Product product, int quantity) {
        if (product.getStockQuantity() < quantity) {
            throw new BadRequestException("Insufficient stock for product: " + product.getName());
        }
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }
}