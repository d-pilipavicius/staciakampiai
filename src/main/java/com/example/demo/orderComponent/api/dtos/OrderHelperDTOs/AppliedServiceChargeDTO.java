package com.example.demo.orderComponent.api.dtos.OrderHelperDTOs;

import com.example.demo.serviceChargeComponent.domain.entities.enums.Currency;
import com.example.demo.serviceChargeComponent.domain.entities.enums.PricingStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppliedServiceChargeDTO {
    private UUID id;
    private UUID chargedByEmployeeId;
    private String title;
    private PricingStrategy valueType;
    private BigDecimal value;
    private BigDecimal amount;
    private Currency currency;
}
