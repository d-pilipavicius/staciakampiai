package com.example.demo.productComponent.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.productComponent.domain.entities.ProductModifier;

public class ProductModifierMapper {

    public static final StaticMapper<PostModifierDTO, ProductModifier> TO_MODEL = dto -> {
        ProductModifier productModifier = new ProductModifier();
        productModifier.setTitle(dto.getTitle());
        productModifier.setQuantityInStock(dto.getQuantityInStock());
        productModifier.setPrice(dto.getPrice().getAmount());
        productModifier.setCurrency(dto.getPrice().getCurrency());
        return productModifier;
    };

    public static final StaticMapper<ProductModifier, ProductModifierDTO> TO_DTO = entity -> {
        ProductModifierDTO productModifierDTO = new ProductModifierDTO();
        productModifierDTO.setId(entity.getId());
        productModifierDTO.setTitle(entity.getTitle());
        productModifierDTO.setQuantityInStock(entity.getQuantityInStock());
        productModifierDTO.setPrice(new MoneyDTO(entity.getPrice(), entity.getCurrency()));
        productModifierDTO.setBusinessId(entity.getBusinessId());
        return productModifierDTO;
    };
}
