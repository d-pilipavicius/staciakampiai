package com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs;

import com.example.demo.CommonHelper.enums.Currency;

import com.example.demo.CommonHelper.enums.PricingStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ServiceChargeDTO {
    @NotNull
    private UUID id;

    @NotNull
    private String title;

    @NotNull
    private PricingStrategy valueType;

    @NotNull
    private BigDecimal serviceChargeValue;


    private Optional<Currency> currency;

    @NotNull
    private UUID businessId;
}
