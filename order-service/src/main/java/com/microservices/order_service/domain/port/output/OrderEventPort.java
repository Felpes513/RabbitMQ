package com.microservices.order_service.domain.port.output;

import com.microservices.order_service.domain.model.Order;

public interface OrderEventPort {
    void publishOrderCreated(Order order);
}