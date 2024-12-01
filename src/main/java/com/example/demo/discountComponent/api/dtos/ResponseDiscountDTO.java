package com.example.demo.discountComponent.api.dtos;


import com.example.demo.discountComponent.api.dtos.DiscountHelperDTOs.DiscountDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

/**
 * A response discount DTO for Patch and Post requests
 */
public class ResponseDiscountDTO {
    @NotNull
    private DiscountDTO discount;
}
