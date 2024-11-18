package com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs;

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
public class SelectedProductDTO {
    private UUID productId;
    private int quantity;
    private List<UUID> selectedModifierIds;
    private List<SelectedDiscountDTO> discounts;
}
