package com.example.ecommerce.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.dto.OrderItemDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    //  Méthode create avec DTO
    public Order create(OrderDTO orderDTO) {
        // Vérifier le client
        Customer customer = customerService.getById(orderDTO.getCustomerId());
        if (customer == null) {
            throw new BadRequestException("Client non trouvé");
        }
        Order order = new Order();
        order.setCustomer(customer);
        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        // Vérifier stock et calcul total
        for (OrderItemDTO itemDTO : orderDTO.getItems()) {
            Product product = productService.getById(itemDTO.getProductId());
            if (product == null) {
                throw new BadRequestException("Produit non trouvé: " + itemDTO.getProductId());
            }
            if (itemDTO.getQuantity() <= 0) {
                throw new BadRequestException("La quantité doit être au moins 1");
            }
            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new BadRequestException("Stock insuffisant pour le produit: " + product.getName());
            }
            // Réduire le stock
            productService.reduceStock(product, itemDTO.getQuantity());
            // Créer OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }
    //  Récupérer toutes les commandes
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
    //  Récupérer une commande par id
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée"));
    }
    //  Mettre à jour le status
    public Order updateStatus(Long id, OrderStatus status) {
        Order order = getById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
    //  Supprimer une commande
    public void delete(Long id) {
        Order order = getById(id);
        orderRepository.delete(order);
    }
}