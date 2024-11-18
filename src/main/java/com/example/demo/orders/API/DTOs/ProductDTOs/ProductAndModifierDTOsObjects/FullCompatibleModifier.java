package com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects;

import com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs.PostModifierDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullCompatibleModifier extends PostModifierDTO {
    private UUID id;
}
