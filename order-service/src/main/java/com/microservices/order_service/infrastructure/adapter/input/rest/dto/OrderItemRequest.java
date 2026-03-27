package com.microservices.order_service.infrastructure.adapter.input.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {

    @NotBlank(message = "ProductId é obrigatório")
    private String productId;

    @NotBlank(message = "ProductName é obrigatório")
    private String productName;

    @NotNull(message = "Quantity é obrigatório")
    @Min(value = 1, message = "Quantity deve ser no mínimo 1")
    private Integer quantity;

    @NotNull(message = "Price é obrigatório")
    @Min(value = 0, message = "Price deve ser positivo")
    private Double price;
}