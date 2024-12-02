package com.example.demo.productComponent.api.dtos;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import jakarta.annotation.Nullable;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
public class PatchProductDTO {
    private final Optional<String> title;
    private final Optional<Integer> quantityInStock;
    private final Optional<MoneyDTO> price;
    private final Optional<List<UUID>> compatibleModifierIds;
}
