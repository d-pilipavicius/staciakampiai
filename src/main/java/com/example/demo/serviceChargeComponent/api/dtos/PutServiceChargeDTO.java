package com.example.demo.serviceChargeComponent.api.dtos;

import com.example.demo.serviceChargeComponent.domain.entities.enums.Currency;
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
    private Optional<String> title;
    private Optional<PricingStrategy> valueType;
    private Optional<BigDecimal> serviceChargeValue;
    private Optional<Currency> currency;
}
