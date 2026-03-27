package com.microservices.order_service.infrastructure.adapter.input.rest.dto;

import com.microservices.order_service.domain.model.Order;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private String id;
    private String userId;
    private List<ItemResponse> items;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;

    @Data
    @Builder
    public static class ItemResponse {
        private String productId;
        private String productName;
        private Integer quantity;
        private Double price;
    }

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .items(order.getItems().stream()
                        .map(item -> ItemResponse.builder()
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
}