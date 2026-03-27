package com.microservices.order_service.domain.port.output;

import com.microservices.order_service.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(String id);
    List<Order> findByUserId(String userId);
}