package com.example.demo.taxComponent.api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PutTaxDTO {
    @NotNull
    private final String title;
    @NotNull
    private final BigDecimal ratePercentage;
    @NotEmpty
    private final List<UUID> entitledProductIds;
}
