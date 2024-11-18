package com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs;

import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects.FullCompatibleModifier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetModifiersDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<FullCompatibleModifier> items;
}
