package com.example.demo.OrderComponent.API.DTOs;

import com.example.demo.helper.enums.Currency;
import com.example.demo.serviceChargeComponent.domain.entities.enums.PricingStrategy;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Builder
public class AppliedServiceChargeDTO {
    private UUID id;
    private UUID chargedByEmployeeId;
    private String title;
    private PricingStrategy valueType;
    private BigDecimal value;
    private BigDecimal amount;
    private Currency currency;
    private UUID businessId;
}
