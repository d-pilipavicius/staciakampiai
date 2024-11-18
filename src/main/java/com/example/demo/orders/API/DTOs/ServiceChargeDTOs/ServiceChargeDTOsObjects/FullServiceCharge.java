package com.example.demo.orders.API.DTOs.ServiceChargeDTOs.ServiceChargeDTOsObjects;

import com.example.demo.orders.domain.entities.enums.Currency;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class FullServiceCharge {
    private UUID id;
    private String title;
    private PricingStrategy valueType;
    private BigDecimal value;
    private Optional<Currency> currency;
    private UUID businessId;
}
