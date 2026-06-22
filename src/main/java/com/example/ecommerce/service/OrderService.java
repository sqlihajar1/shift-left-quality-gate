package com.example.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.dto.OrderItemDTO;
import com.example.ecommerce.dto.EntityMapper;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.exception.BadRequestException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    public OrderDTO create(OrderDTO orderDTO) {
        Customer customer = customerService.findById(orderDTO.getCustomerId());
        Order order = new Order();
        order.setCustomer(customer);
        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO itemDTO : orderDTO.getItems()) {
            Product product = productService.findById(itemDTO.getProductId());
            if (itemDTO.getQuantity() <= 0) {
                throw new BadRequestException("La quantité doit être au moins 1");
            }
            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new BadRequestException("Stock insuffisant pour: " + product.getName());
            }
            productService.reduceStock(product, itemDTO.getQuantity());
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);
            total = total.add(product.getPrice()
                    .multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING);
        return EntityMapper.toDTO(orderRepository.save(order));
    }

    public List<OrderDTO> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(EntityMapper::toDTO)
                .toList();
    }

    public OrderDTO getById(Long id) {
        return EntityMapper.toDTO(findById(id));
    }

    public OrderDTO updateStatus(Long id, OrderStatus status) {
        Order order = findById(id);
        order.setStatus(status);
        return EntityMapper.toDTO(orderRepository.save(order));
    }

    public void delete(Long id) {
        orderRepository.delete(findById(id));
    }

    private Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée"));
    }
}