package com.microservices.order_service.infrastructure.adapter.output.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDocument {
    private String productId;
    private String productName;
    private Integer quantity;
    private Double price;
}