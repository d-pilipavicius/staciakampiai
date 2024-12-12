package com.example.demo.OrderComponent.API.DTOs;

import com.example.demo.helper.enums.Currency;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class OrderItemModifierDTO {
    private UUID id;
    private String title;
    private BigDecimal price;
    private Currency currency;
}