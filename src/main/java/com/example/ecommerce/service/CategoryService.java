package com.example.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.dto.EntityMapper;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.exception.ResourceNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = EntityMapper.toEntity(categoryDTO);
        return EntityMapper.toDTO(categoryRepository.save(category));
    }

    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(EntityMapper::toDTO)
                .toList();
    }

    public CategoryDTO getById(Long id) {
        return EntityMapper.toDTO(findById(id));
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        Category category = findById(id);
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return EntityMapper.toDTO(categoryRepository.save(category));
    }

    public void delete(Long id) {
        categoryRepository.delete(findById(id));
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}