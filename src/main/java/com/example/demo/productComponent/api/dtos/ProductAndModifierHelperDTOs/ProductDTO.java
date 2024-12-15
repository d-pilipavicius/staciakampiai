package com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
public class ProductDTO {
    @NotNull(message = "ID is required")
    private final UUID id;

    @NotNull(message = "Title is required")
    private final String title;

    @NotNull(message = "Quantity in stock is required")
    private final int quantityInStock;

    @NotNull(message = "Price is required")
    private final MoneyDTO price;

    private List<ProductModifierDTO> compatibleModifiers;

    @NotNull(message = "Business ID is required")
    private final UUID businessId;
}
