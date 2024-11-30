package com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductModifierDTO {
    @NonNull
    private UUID id;
    @NonNull
    private String title;
    private int quantityInStock;
    @NonNull
    private MoneyDTO price;
    @NonNull
    private UUID businessId;
}
