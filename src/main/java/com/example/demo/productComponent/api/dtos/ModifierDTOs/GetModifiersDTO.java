package com.example.demo.productComponent.api.dtos.ModifierDTOs;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
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
