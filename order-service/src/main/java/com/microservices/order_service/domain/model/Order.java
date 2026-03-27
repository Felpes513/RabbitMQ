package com.microservices.order_service.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Order {

    private final String id;
    private final String userId;
    private final List<OrderItem> items;
    private final Double totalAmount;
    private final OrderStatus status;
    private final LocalDateTime createdAt;

    public enum OrderStatus{
        PENDING, CONFIRMED, CANCELLED
    }

    public boolean isCancellable(){
        return this.status == OrderStatus.PENDING;
    }

    public Double calculateTotal(){
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
