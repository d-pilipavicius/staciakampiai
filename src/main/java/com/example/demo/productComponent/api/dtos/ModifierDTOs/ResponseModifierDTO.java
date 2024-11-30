package com.example.demo.productComponent.api.dtos.ModifierDTOs;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * A response product modifier DTO for Patch and Post requests
 */
public class ResponseModifierDTO {
    private ProductModifierDTO productModifier;
}
