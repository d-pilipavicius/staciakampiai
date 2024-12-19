package com.example.demo.taxComponent.api.dtos;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Setter;

@Getter
@Builder
@Setter
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

    @NotEmpty
    private List<@Valid ProductDTO> entitledProducts;
}
