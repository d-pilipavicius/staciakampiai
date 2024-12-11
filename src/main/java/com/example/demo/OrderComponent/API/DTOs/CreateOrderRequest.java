package com.example.demo.OrderComponent.API.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderRequest {
    @NonNull
    private UUID businessId;
    @NonNull
    private UUID createdByEmployeeId;
    @NonNull
    private List<OrderItemRequest> items;
    @NonNull
    private UUID reservationId;
}