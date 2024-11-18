package com.example.demo.orders.API.DTOs.ProductDTOs;

import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects.Price;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class PatchProductDTO {
    private Optional<String> title;
    private Optional<Integer> quantityInStock;
    private Optional<Price> price;
    private Optional<List<UUID>> compatibleModifierIds;
}
