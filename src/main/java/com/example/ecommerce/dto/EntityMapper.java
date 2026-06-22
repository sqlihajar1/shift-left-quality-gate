package com.example.ecommerce.dto;

import com.example.ecommerce.entity.*;

public class EntityMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null) return null;
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public static CustomerDTO toDTO(Customer customer) {
        if (customer == null) return null;
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        return dto;
    }

    public static Customer toEntity(CustomerDTO dto) {
        if (dto == null) return null;
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        return customer;
    }

    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            ProductDTO.CategoryRef ref = new ProductDTO.CategoryRef();
            ref.setId(product.getCategory().getId());
            dto.setCategory(ref);
        }
        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        if (dto == null) return null;
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        return product;
    }

    public static OrderDTO toDTO(Order order) {
        if (order == null) return null;
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setStatus(order.getStatus() != null ? order.getStatus().name() : null);
        dto.setTotalAmount(order.getTotalAmount());

        //  customer.id pour les tests Karate
        if (order.getCustomer() != null) {
            dto.setCustomerId(order.getCustomer().getId());
            OrderDTO.CustomerRef customerRef = new OrderDTO.CustomerRef();
            customerRef.setId(order.getCustomer().getId());
            customerRef.setFirstName(order.getCustomer().getFirstName());
            customerRef.setLastName(order.getCustomer().getLastName());
            customerRef.setEmail(order.getCustomer().getEmail());
            dto.setCustomer(customerRef);
        }

        if (order.getOrderItems() != null) {
            dto.setItems(order.getOrderItems().stream()
                    .map(item -> {
                        OrderItemDTO itemDTO = new OrderItemDTO();
                        if (item.getProduct() != null) {
                            itemDTO.setProductId(item.getProduct().getId());
                        }
                        itemDTO.setQuantity(item.getQuantity());
                        return itemDTO;
                    })
                    .toList());
        }
        return dto;
    }
}