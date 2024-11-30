package com.example.demo.productComponent.api.dtos.ModifierDTOs;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchModifierDTO {
    private Optional<String> title = Optional.empty();
    private Optional<Integer> quantityInStock = Optional.empty();
    private Optional<MoneyDTO> price = Optional.empty();
}
