package com.example.demo.productComponent.api.dtos;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchProductDTO {
    private Optional<String> title;
    private Optional<Integer> quantityInStock;
    private Optional<MoneyDTO> price;
    private Optional<List<UUID>> compatibleModifierIds;
}
