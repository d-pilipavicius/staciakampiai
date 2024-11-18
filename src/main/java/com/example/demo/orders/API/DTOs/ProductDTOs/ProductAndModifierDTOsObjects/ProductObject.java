package com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductObject {
    private UUID id;
    private String title;
    private int quantityInStock;
    private Money price;
    private List<ProductModifierObject> compatibleModifiers;
    private UUID businessId;
}
