package com.example.demo.OrderComponent.API.DTOs;

import com.example.demo.CommonHelper.enums.Currency;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class OrderItemModifierDTO {
    private final UUID id;
    private final String title;
    private final BigDecimal price;
    private final Currency currency;
    private final UUID productModifierId;
}