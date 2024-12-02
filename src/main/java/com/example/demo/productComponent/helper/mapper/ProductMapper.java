package com.example.demo.productComponent.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.productComponent.domain.entities.Product;

public class ProductMapper {

    public static final StaticMapper<PostProductDTO, Product> TO_MODEL = dto -> Product.builder()
            .title(dto.getTitle())
            .quantityInStock(dto.getQuantityInStock())
            .price(dto.getPrice().getAmount())
            .currency(dto.getPrice().getCurrency())
            .businessId(dto.getBusinessId())
            .build();

    // Note: does not set compatibleModifiers directly -> it is set in the services
    public static final StaticMapper<Product, ProductDTO> TO_DTO = entity -> ProductDTO.builder()
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
}
