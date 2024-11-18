package com.example.demo.orders.API.DTOs.ProductDTOs;

import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects.Money;
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
public class PostProductDTO {
    private String title;
    private int quantityInStock;
    private Money price;
    private List<UUID> compatibleModifierIds;
    private UUID businessId;

}
