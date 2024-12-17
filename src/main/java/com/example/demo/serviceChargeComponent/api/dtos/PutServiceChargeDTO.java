package com.example.demo.serviceChargeComponent.api.dtos;

import com.example.demo.CommonHelper.enums.Currency;
import com.example.demo.serviceChargeComponent.domain.entities.enums.PricingStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
public class PutServiceChargeDTO {
    private final Optional<String> title;
    private final Optional<PricingStrategy> valueType;
    private final Optional<BigDecimal> serviceChargeValue;
    private final Optional<Currency> currency;
}
