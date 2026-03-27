package com.microservices.order_service.infrastructure.adapter.input.rest;

import com.microservices.order_service.domain.model.OrderItem;
import com.microservices.order_service.domain.port.input.OrderUseCase;
import com.microservices.order_service.infrastructure.adapter.input.rest.dto.CreateOrderRequest;
import com.microservices.order_service.infrastructure.adapter.input.rest.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderUseCase orderUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request,
            Principal principal) {

        List<OrderItem> items = request.getItems().stream()
                .map(item -> OrderItem.builder()
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .toList();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderResponse.from(orderUseCase.createOrder(principal.getName(), items)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(
            @PathVariable String id,
            Principal principal) {
        return ResponseEntity.ok(OrderResponse.from(orderUseCase.findById(id, principal.getName())));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>> findMyOrders(Principal principal) {
        return ResponseEntity.ok(orderUseCase.findByUserId(principal.getName())
                .stream()
                .map(OrderResponse::from)
                .toList());
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable String id,
            Principal principal) {
        return ResponseEntity.ok(OrderResponse.from(orderUseCase.cancelOrder(id, principal.getName())));
    }
}