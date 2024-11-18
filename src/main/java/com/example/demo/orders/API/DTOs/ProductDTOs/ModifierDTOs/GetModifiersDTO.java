package com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs;

import com.example.demo.orders.API.DTOs.BaseDTOs.GetBaseDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects.ProductModifierObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetModifiersDTO extends GetBaseDTO {
    private List<ProductModifierObject> items;
}
