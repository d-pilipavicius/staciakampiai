package com.example.demo.productComponent.api.dtos;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class GetProductsDTO {
    private final int totalItems;
    private final int totalPages;
    private final int currentPage;
    private List<ProductDTO> items;
    private final UUID businessId;
}
