package com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierHelperDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductModifierDTO {
    private UUID id;
    private String title;
    private int quantityInStock;
    private MoneyDTO price;
    private UUID businessId;
}
