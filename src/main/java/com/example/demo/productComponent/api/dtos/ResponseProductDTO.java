package com.example.demo.productComponent.api.dtos;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import lombok.*;

@Getter
@Builder
/**
 * A response product DTO for Patch and Post requests
 */
public class ResponseProductDTO {
    private final ProductDTO product;
}
