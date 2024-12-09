package com.example.demo.productComponent.helper.factories;

import com.example.demo.productComponent.api.dtos.ProductCompatibleModifierDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductFactory {
    public static ProductCompatibleModifierDTO createProductCompatibleModifierDTO(UUID productId, UUID modifierId) {
        return ProductCompatibleModifierDTO.builder().productId(productId).modifierId(modifierId).build();
    }
}
