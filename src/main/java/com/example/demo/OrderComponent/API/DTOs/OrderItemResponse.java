package com.example.demo.OrderComponent.API.DTOs;

import com.example.demo.OrderComponent.Domain.Entities.Enums.Currency;
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
public class OrderItemResponse {
    @NonNull
    private UUID id;
    @NonNull
    private UUID productId;
    @NonNull
    private String title;
    @NonNull
    private int quantity;
    @NonNull
    private UnitPrice unitPrice;
    @NonNull
    private BigDecimal originalPrice;
    @NonNull
    private Currency currency;
    private List<OrderItemModifierResponse> modifiers;
    @Getter
    @Builder
    @AllArgsConstructor
    public static class UnitPrice {
        @NonNull
        private BigDecimal base;
        @NonNull
        private BigDecimal withModifiers;
    }
}