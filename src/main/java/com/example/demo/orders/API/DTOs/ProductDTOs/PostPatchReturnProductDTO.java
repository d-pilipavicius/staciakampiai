package com.example.demo.orders.API.DTOs.ProductDTOs;

import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects.ProductObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPatchReturnProductDTO {
    private ProductObject product;
}
