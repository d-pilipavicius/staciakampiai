package com.example.demo.orders.API.DTOs.DiscountDTO;


import com.example.demo.orders.API.DTOs.DiscountDTO.DiscountHelperDTOs.DiscountDTO;
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
