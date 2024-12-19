package com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Getter
@Builder
@ToString
public class ProductModifierDTO {
    @NotNull(message = "ID is required")
    private final UUID id;

    @NotNull(message = "Title is required")
    private final String title;

    @NotNull(message = "Quantity in stock is required")
    private final int quantityInStock;

    @NotNull(message = "Price is required")
    private final MoneyDTO price;

    @NotNull(message = "Business ID is required")
    private final UUID businessId;
}
