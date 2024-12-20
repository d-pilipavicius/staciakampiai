package com.example.demo.productComponent.api.dtos.ModifierDTOs;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
public class PutModifierDTO {
    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @Min(0)
    private Integer quantityInStock;

    @NotNull
    private MoneyDTO price;
}
