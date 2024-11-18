package com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects;

import com.example.demo.orders.domain.entities.enums.Currency;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ServiceChargeInOrder {
    private UUID id;
    private UUID chargedByEmployeeId;
    private String title;
    private PricingStrategy valueType;
    private BigDecimal value;
    private BigDecimal amount;
    private Currency currency;
}
