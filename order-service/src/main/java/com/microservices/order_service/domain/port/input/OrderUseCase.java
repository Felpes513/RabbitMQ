package com.microservices.order_service.domain.port.input;

import com.microservices.order_service.domain.model.Order;
import com.microservices.order_service.domain.model.OrderItem;

import java.util.List;

public interface OrderUseCase {
    Order createOrder(String userId, List<OrderItem> items);
    Order findById(String orderId, String userId);
    List<Order> findByUserId(String userId);
    Order cancelOrder(String orderId, String userId);
}