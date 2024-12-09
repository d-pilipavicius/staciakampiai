package com.example.demo.productComponent.api.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProductCompatibleModifierDTO {
    private final UUID productId;
    private final UUID modifierId;
}
