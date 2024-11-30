package com.example.demo.productComponent.api.dtos;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchProductDTO {
    private Optional<String> title = Optional.empty();
    private Optional<Integer> quantityInStock = Optional.empty();
    private Optional<MoneyDTO> price = Optional.empty();
    private Optional<List<UUID>> compatibleModifierIds = Optional.empty();
}
