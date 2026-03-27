package com.microservices.order_service.infrastructure.adapter.output.messaging;

import com.microservices.order_service.domain.model.Order;
import com.microservices.order_service.domain.port.output.OrderEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher implements OrderEventPort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishOrderCreated(Order order) {
        log.info("Publicando evento order.created para orderId: {}", order.getId());
        rabbitTemplate.convertAndSend("order.exchange", "order.created", order.getId());
    }
}