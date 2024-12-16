package com.example.demo.productComponent.helper.mapper;

import com.example.demo.CommonHelper.mapper.base.StaticMapper;
import com.example.demo.productComponent.api.dtos.ProductCompatibleModifierDTO;
import com.example.demo.productComponent.domain.entities.ProductCompatibleModifier;

public class ProductCompatibleModifierMapper {

    public static final StaticMapper<ProductCompatibleModifierDTO, ProductCompatibleModifier> TO_MODEL = dto -> ProductCompatibleModifier
            .builder()
            .productId(dto.getProductId())
            .modifierId(dto.getModifierId())
            .build();
}
