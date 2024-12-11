package com.example.demo.OrderComponent.API.DTOs;

import com.example.demo.OrderComponent.Domain.Entities.Enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderItemModifierResponse {
    private UUID id;
    private String title;
    private BigDecimal price;
    private Currency currency;
}