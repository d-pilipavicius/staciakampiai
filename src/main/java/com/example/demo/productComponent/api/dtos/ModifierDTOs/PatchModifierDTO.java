package com.example.demo.productComponent.api.dtos.ModifierDTOs;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import lombok.*;

import java.util.Optional;

@Getter
@Builder
public class PatchModifierDTO {
    private Optional<String> title;
    private Optional<Integer> quantityInStock;
    private Optional<MoneyDTO> price;
}
