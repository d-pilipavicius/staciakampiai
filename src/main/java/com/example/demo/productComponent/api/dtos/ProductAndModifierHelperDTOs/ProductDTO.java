package com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private UUID id;
    private String title;
    private int quantityInStock;
    private MoneyDTO price;
    private List<ProductModifierDTO> compatibleModifiers;
    private UUID businessId;
}
