package com.example.demo.taxComponent.api.dtos.TaxHelperDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class TaxDTO {
    @NotNull
    private final UUID id;

    @NotNull
    private final String title;

    @NotNull
    private final BigDecimal ratePercentage;

    @NotNull
    private final UUID businessId;
}
