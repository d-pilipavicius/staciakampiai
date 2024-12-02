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
    @NotNull
    private final UUID id;
    @NotNull
    private final String title;
    private final int quantityInStock;
    @NotNull
    private final MoneyDTO price;
    private List<ProductModifierDTO> compatibleModifiers;
    @NotNull
    private final UUID businessId;
}
