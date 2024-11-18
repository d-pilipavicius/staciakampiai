package com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SelectedProduct {
    private UUID productId;
    private int quantity;
    private List<UUID> selectedModifierIds;
    private List<SelectedDiscount> discounts;
}
