package com.example.demo.OrderComponent.API.DTOs;


import com.example.demo.OrderComponent.Domain.Entities.Enums.Currency;
import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderResponse {
    @NonNull
    private UUID id;
    @NonNull
    private UUID employeeId;
    @NonNull
    private UUID reservationId;
    @NonNull
    private OrderStatus status;
    @NonNull
    private LocalDateTime createdAt;
    @NonNull
    private BigDecimal originalPrice;
    @NonNull
    private Currency currency;
    @NonNull
    private List<OrderItemResponse> items;
    @NonNull
    private UUID businessId;
}
