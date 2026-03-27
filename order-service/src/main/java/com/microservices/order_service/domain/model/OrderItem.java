package com.microservices.order_service.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItem {

    private final String productId;
    private final String productName;
    private final Integer quantity;
    private final Double price;
}
