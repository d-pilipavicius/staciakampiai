package com.example.demo.productComponent.api.dtos.ModifierDTOs;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class GetModifiersDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<ProductModifierDTO> items;
}
