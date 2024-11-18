package com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs;

import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects.FullCompatibleModifier;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PostPatchReturnModifierDTO {
    private FullCompatibleModifier productModifier;
}
