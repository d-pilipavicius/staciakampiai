package com.example.demo.OrderComponent.API.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderItemRequest {
    @NonNull
    private UUID productId;
    @NonNull
    private int quantity;
    private List<UUID> selectedModifierIds;
}