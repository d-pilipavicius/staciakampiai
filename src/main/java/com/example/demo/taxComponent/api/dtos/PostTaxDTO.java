package com.example.demo.taxComponent.api.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class PostTaxDTO {

    @NotNull
    private final String title;

    @NotNull
    private final BigDecimal ratePercentage;

    @NotNull
    private final UUID businessId;

    @NotEmpty
    private final List<UUID> entitledProductIds;
}
