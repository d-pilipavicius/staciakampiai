package com.example.demo.productComponent.api.dtos.ModifierDTOs;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
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
