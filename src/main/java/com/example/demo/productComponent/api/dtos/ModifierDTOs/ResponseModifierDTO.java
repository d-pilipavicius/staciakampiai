package com.example.demo.productComponent.api.dtos.ModifierDTOs;

import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * A response product modifier DTO for Patch and Post requests
 */
public class ResponseModifierDTO {
    private ProductModifierDTO productModifier;
}
