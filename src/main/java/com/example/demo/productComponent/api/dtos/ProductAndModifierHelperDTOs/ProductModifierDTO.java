package com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Getter
@Builder
public class ProductModifierDTO {
    @NotNull
    private final UUID id;
    @NotNull
    private final String title;
    private final int quantityInStock;
    @NotNull
    private final MoneyDTO price;
    @NotNull
    private final UUID businessId;
}
