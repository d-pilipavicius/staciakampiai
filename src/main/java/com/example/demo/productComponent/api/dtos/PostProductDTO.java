package com.example.demo.productComponent.api.dtos;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class PostProductDTO {
    @NotNull(message = "Title is required")
    private final String title;

    @Min(value = 0, message = "Quantity in stock must be greater than or equal to 0")
    private final int quantityInStock;

    @NotNull
    @Positive(message = "Price must be greater than or equal to 0")
    private final MoneyDTO price;

    private final List<UUID> compatibleModifierIds;

    @NotNull(message = "Business ID is required")
    private final UUID businessId;
}
