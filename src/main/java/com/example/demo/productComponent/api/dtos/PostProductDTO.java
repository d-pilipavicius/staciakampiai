package com.example.demo.productComponent.api.dtos;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostProductDTO {
    @NonNull
    private String title;
    private int quantityInStock;
    @NonNull
    private MoneyDTO price;
    private List<UUID> compatibleModifierIds;
    @NonNull
    private UUID businessId;
}
