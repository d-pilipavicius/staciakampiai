package com.example.demo.orders.api.DTOs.ServiceChargeDTOs;

import com.example.demo.orders.domain.entities.enums.Currency;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class PostServiceChargeDTO {
    private String title;
    private PricingStrategy valueType;
    private BigDecimal value;
    private Optional<Currency> currency;
    private UUID businessId;
}
