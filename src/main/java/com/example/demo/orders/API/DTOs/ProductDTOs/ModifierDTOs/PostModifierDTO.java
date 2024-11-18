package com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs;

import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostModifierDTO {
    private String title;
    private int quantityInStock;
    private Price price;
    private UUID businessId;
}
