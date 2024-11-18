package com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs;

import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects.Price;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class PatchModifierDTO {
    private Optional<String> title;
    private Optional<Integer> quantityInStock;
    private Optional<Price> price;
}
