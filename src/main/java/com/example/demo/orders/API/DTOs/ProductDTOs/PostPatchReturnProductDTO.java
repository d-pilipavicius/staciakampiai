package com.example.demo.orders.API.DTOs.ProductDTOs;

import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects.FullProduct;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPatchReturnProductDTO {
    private FullProduct product;
}
