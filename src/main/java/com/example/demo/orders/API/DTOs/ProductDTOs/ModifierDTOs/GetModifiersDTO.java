package com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs;

import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierHelperDTOs.ProductModifierDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetModifiersDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<ProductModifierDTO> items;

    public GetModifiersDTO(List<ProductModifierDTO> items){
        this.items = items;
    }
}
