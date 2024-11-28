package com.example.demo.discountComponent.api.dtos;


import com.example.demo.discountComponent.api.dtos.DiscountHelperDTOs.DiscountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * A response discount DTO for Patch and Post requests
 */
public class ResponseDiscountDTO {
    private DiscountDTO discount;
}
