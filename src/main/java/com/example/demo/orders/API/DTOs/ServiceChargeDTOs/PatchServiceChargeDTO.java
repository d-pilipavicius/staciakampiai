package com.example.demo.orders.API.DTOs.ServiceChargeDTOs;

import com.example.demo.orders.domain.entities.enums.Currency;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchServiceChargeDTO {
    private Optional<String> title;
    private Optional<PricingStrategy> valueType;
    private Optional<BigDecimal> value;
    private Optional<Currency> currency;
}
