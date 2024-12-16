package com.example.demo.serviceChargeComponent.api.dtos;

import com.example.demo.CommonHelper.enums.Currency;
import com.example.demo.serviceChargeComponent.domain.entities.enums.PricingStrategy;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PostServiceChargeDTO {
    @NotNull
    private final String title;

    @NotNull
    private final PricingStrategy valueType;

    @NotNull
    private final BigDecimal serviceChargeValue;


    private final Optional<Currency> currency;

    @NotNull
    private final UUID businessId;
}
