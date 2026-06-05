package com.example.ecommerce.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce.entity.Product;
public interface ProductRepository extends JpaRepository<Product, Long> {
    // méthode pour chercher par nom si besoin
    boolean existsByName(String name);
}