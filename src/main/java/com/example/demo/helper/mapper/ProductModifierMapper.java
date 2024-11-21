package com.example.demo.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs.PostModifierDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierHelperDTOs.MoneyDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.orders.domain.entities.ProductModifier;

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
