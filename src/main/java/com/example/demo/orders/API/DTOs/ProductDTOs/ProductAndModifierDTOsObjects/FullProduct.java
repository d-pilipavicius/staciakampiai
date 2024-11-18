package com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class FullProduct {
    private UUID id;
    private String title;
    private int quantityInStock;
    private Price price;
    private List<FullCompatibleModifier> compatibleModifiers;
    private UUID businessId;
}
