package com.microservices.order_service.application.service;

import com.microservices.order_service.domain.model.Order;
import com.microservices.order_service.domain.model.Order.OrderStatus;
import com.microservices.order_service.domain.model.OrderItem;
import com.microservices.order_service.domain.port.input.OrderUseCase;
import com.microservices.order_service.domain.port.output.OrderEventPort;
import com.microservices.order_service.domain.port.output.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderRepositoryPort orderRepository;
    private final OrderEventPort orderEventPort;

    @Override
    public Order createOrder(String userId, List<OrderItem> items) {
        Order order = Order.builder()
                .userId(userId)
                .items(items)
                .totalAmount(items.stream()
                        .mapToDouble(i -> i.getPrice() * i.getQuantity())
                        .sum())
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Order saved = orderRepository.save(order);
        orderEventPort.publishOrderCreated(saved);
        return saved;
    }

    @Override
    public Order findById(String orderId, String userId) {
        return orderRepository.findById(orderId)
                .filter(order -> order.getUserId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    @Override
    public List<Order> findByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order cancelOrder(String orderId, String userId) {
        Order order = findById(orderId, userId);

        if (!order.isCancellable()) {
            throw new IllegalStateException("Pedido não pode ser cancelado");
        }

        Order cancelled = Order.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .items(order.getItems())
                .totalAmount(order.getTotalAmount())
                .status(OrderStatus.CANCELLED)
                .createdAt(order.getCreatedAt())
                .build();

        return orderRepository.save(cancelled);
    }
}