package com.example.ecommerce.dto;
import lombok.*;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private Integer quantity;
}