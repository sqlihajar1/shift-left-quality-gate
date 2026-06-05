package com.example.ecommerce.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity

@Getter @Setter

@NoArgsConstructor @AllArgsConstructor

@Table(name = "customers", uniqueConstraints = {

        @UniqueConstraint(columnNames = "email")

})

public class Customer {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank(message = "Le prénom ne peut pas être vide")

    @Column(nullable = false)

    private String firstName;

    @NotBlank(message = "Le nom ne peut pas être vide")

    @Column(nullable = false)

    private String lastName;

    @NotBlank(message = "L'email est obligatoire")

    @Email(message = "L'email doit être valide")

    @Column(nullable = false, unique = true)

    private String email;

    private String phone;

    private LocalDateTime createdAt;

    @PrePersist

    public void prePersist() {

        this.createdAt = LocalDateTime.now();

    }


    public void setFirstName(String firstName) {

        this.firstName = (firstName != null) ? firstName.trim() : null;

    }

    public void setLastName(String lastName) {

        this.lastName = (lastName != null) ? lastName.trim() : null;

    }

    public void setEmail(String email) {

        this.email = (email != null) ? email.trim() : null;

    }

}
