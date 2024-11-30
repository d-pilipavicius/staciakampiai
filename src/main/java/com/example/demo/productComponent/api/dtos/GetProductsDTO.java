package com.example.demo.productComponent.api.dtos;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductsDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<ProductDTO> items;
    private UUID businessId;

    public GetProductsDTO(List<ProductDTO> items, UUID businessId, int totalItems) {
        this.items = items;
        this.businessId = businessId;
        this.totalItems = totalItems;
    }
}
