package com.example.demo.productComponent.api.dtos;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * A response product DTO for Patch and Post requests
 */
public class ResponseProductDTO {
    private ProductDTO product;
}
