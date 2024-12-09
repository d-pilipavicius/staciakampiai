package com.example.demo.productComponent.api.dtos.ModifierDTOs;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class PostModifierDTO {
    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Quantity in stock is required")
    @Min(value = 0, message = "Quantity in stock must be greater than or equal to 0")
    private int quantityInStock;

    @NotNull(message = "Price is required")
    private MoneyDTO price;

    @NotNull(message = "Business ID is required")
    private UUID businessId;
}
