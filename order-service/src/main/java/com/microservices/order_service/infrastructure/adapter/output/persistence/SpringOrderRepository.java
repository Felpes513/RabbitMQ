package com.microservices.order_service.infrastructure.adapter.output.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringOrderRepository extends MongoRepository<OrderDocument, String> {
    List<OrderDocument> findByUserId(String userId);
}