package com.example.demo.productComponent.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.GetModifiersDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.productComponent.domain.entities.ProductModifier;
import org.springframework.data.domain.Page;

import java.util.List;

public class ProductModifierMapper {

    public static final StaticMapper<PostModifierDTO, ProductModifier> TO_MODEL = dto -> ProductModifier.builder()
            .title(dto.getTitle())
            .quantityInStock(dto.getQuantityInStock())
            .price(dto.getPrice().getAmount())
            .currency(dto.getPrice().getCurrency())
            .businessId(dto.getBusinessId())
            .build();

    public static final StaticMapper<ProductModifier, ProductModifierDTO> TO_DTO = entity -> ProductModifierDTO.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .quantityInStock(entity.getQuantityInStock())
            .price(
                    MoneyDTO.builder()
                            .amount(entity.getPrice())
                            .currency(entity.getCurrency())
                            .build()
            )
            .businessId(entity.getBusinessId())
            .build();

    public static final StaticMapper<Page<ProductModifierDTO>, GetModifiersDTO> PAGE_TO_GET_MODIFIERS_DTO = productModifierDTO -> GetModifiersDTO.builder()
            .items(productModifierDTO.getContent())
            .totalItems((int)productModifierDTO.getTotalElements())
            .totalPages(productModifierDTO.getTotalPages())
            .currentPage(productModifierDTO.getPageable().getPageNumber())
            .build();

    public static final StaticMapper<List<ProductModifierDTO>, GetModifiersDTO> LIST_TO_GET_MODIFIERS_DTO = productModifierDTO -> GetModifiersDTO.builder()
            .items(productModifierDTO)
            .totalItems(productModifierDTO.size())
            .build();
}