package com.example.ecommerce.dto;
import lombok.*;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}