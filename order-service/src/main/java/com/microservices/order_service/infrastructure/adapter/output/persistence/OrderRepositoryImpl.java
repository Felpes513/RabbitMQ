package com.microservices.order_service.infrastructure.adapter.output.persistence;

import com.microservices.order_service.domain.model.Order;
import com.microservices.order_service.domain.model.Order.OrderStatus;
import com.microservices.order_service.domain.model.OrderItem;
import com.microservices.order_service.domain.port.output.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryPort {

    private final SpringOrderRepository springOrderRepository;

    @Override
    public Order save(Order order) {
        OrderDocument document = toDocument(order);
        OrderDocument saved = springOrderRepository.save(document);
        return toDomain(saved);
    }

    @Override
    public Optional<Order> findById(String id) {
        return springOrderRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Order> findByUserId(String userId) {
        return springOrderRepository.findByUserId(userId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private OrderDocument toDocument(Order order) {
        return OrderDocument.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .items(order.getItems().stream()
                        .map(item -> OrderItemDocument.builder()
                                .productId(item.getProductId())
                                .productName(item.getProductName())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build())
                        .toList())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .build();
    }

    private Order toDomain(OrderDocument document) {
        return Order.builder()
                .id(document.getId())
                .userId(document.getUserId())
                .items(document.getItems().stream()
                        .map(item -> OrderItem.builder()
                                .productId(item.getProductId())
                                .productName(item.getProductName())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build())
                        .toList())
                .totalAmount(document.getTotalAmount())
                .status(OrderStatus.valueOf(document.getStatus()))
                .createdAt(document.getCreatedAt())
                .build();
    }
}