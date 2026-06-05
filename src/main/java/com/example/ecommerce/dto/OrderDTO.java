package com.example.ecommerce.dto;
import lombok.*;
import java.util.List;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
    private Long customerId;
    private List<OrderItemDTO> items;
}