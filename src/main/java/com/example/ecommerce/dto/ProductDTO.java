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
}