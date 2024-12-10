package com.example.demo.productComponent.api.dtos;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class PutProductDTO {
    @NotNull
    @NotBlank
    private final String title;

    @NotNull
    @Min(0)
    private final int quantityInStock;

    @NotNull
    private final MoneyDTO price;

    private final List<UUID> compatibleModifierIds = Collections.emptyList();
}
