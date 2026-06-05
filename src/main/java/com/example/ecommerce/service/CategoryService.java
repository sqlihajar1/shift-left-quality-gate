package com.example.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.exception.ResourceNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Category create(Category category) {
        return categoryRepository.save(category);
    }
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
    public Category update(Long id, Category updated) {
        Category category = getById(id);
        category.setName(updated.getName());
        category.setDescription(updated.getDescription());
        return categoryRepository.save(category);
    }
    public void delete(Long id) {
        Category category = getById(id);
        categoryRepository.delete(category);
    }

}
