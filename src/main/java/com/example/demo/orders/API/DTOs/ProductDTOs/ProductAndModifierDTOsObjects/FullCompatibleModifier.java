package com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FullCompatibleModifier {
    private UUID id;
    private String title;
    private int quantityInStock;
    private Price price;
    private UUID businessId;
}
