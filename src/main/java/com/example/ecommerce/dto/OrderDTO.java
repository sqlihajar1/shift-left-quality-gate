package com.example.ecommerce.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
    // ✅ Champs attendus par les tests Karate en réponse
    private Long id;
    private CustomerRef customer;
    private Long customerId;
    private List<OrderItemDTO> items;
    private String status;
    private BigDecimal totalAmount;

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CustomerRef {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
    }
}