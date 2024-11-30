package com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NonNull
    private UUID id;
    @NonNull
    private String title;
    private int quantityInStock;
    @NonNull
    private MoneyDTO price;
    private List<ProductModifierDTO> compatibleModifiers;
    @NonNull
    private UUID businessId;
}
