package com.example.demo.productComponent.api.dtos.ModifierDTOs;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchModifierDTO {
    private Optional<String> title;
    private Optional<Integer> quantityInStock;
    private Optional<MoneyDTO> price;
}
