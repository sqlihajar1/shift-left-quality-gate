package com.example.ecommerce.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Long categoryId;

    // ✅ Pour compatibilité avec les tests Karate qui envoient "category": {"id": X}
    private CategoryRef category;

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CategoryRef {
        private Long id;
    }

    // Retourne le categoryId depuis category.id si categoryId est null
    public Long getEffectiveCategoryId() {
        if (categoryId != null) return categoryId;
        if (category != null) return category.getId();
        return null;
    }
}