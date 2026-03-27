package com.microservices.order_service.infrastructure.adapter.output.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class OrderDocument {

    @Id
    private String id;
    private String userId;
    private List<OrderItemDocument> items;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
}