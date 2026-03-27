package com.microservices.order_service.infrastructure.adapter.input.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotEmpty(message = "A lista de itens não pode ser vazia")
    @Valid
    private List<OrderItemRequest> items;
}