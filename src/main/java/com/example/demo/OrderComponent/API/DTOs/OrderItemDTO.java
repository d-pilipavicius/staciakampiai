package com.example.demo.OrderComponent.API.DTOs;

import com.example.demo.helper.enums.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
public class OrderItemDTO {

    private UUID id;
    @NonNull
    private UUID productId;
    private String title;
    @NonNull
    private int quantity;
    private UnitPrice unitPrice;
    private BigDecimal originalPrice;
    private Currency currency;
    private List<UUID> selectedModifierIds;
    private List<OrderItemModifierDTO> modifiers;

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