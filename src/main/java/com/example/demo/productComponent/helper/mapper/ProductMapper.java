package com.example.demo.productComponent.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.productComponent.domain.entities.Product;

public class ProductMapper {

    public static final StaticMapper<PostProductDTO, Product> TO_MODEL = dto -> {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setQuantityInStock(dto.getQuantityInStock());
        product.setAmount(dto.getPrice().getAmount());
        product.setCurrency(dto.getPrice().getCurrency());
        product.setBusinessId(dto.getBusinessId());
        // ProductModifiers should be set in service?
        return product;
    };

    public static final StaticMapper<Product, ProductDTO> TO_DTO = entity -> {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(entity.getId());
        productDTO.setTitle(entity.getTitle());
        productDTO.setQuantityInStock(entity.getQuantityInStock());
        productDTO.setPrice(new MoneyDTO(entity.getAmount(), entity.getCurrency()));
        productDTO.setCompatibleModifiers(
                entity.getProductModifiers().stream().map(
                        ProductModifierMapper.TO_DTO::map
                ).toList()
        );
        productDTO.setBusinessId(entity.getBusinessId());
        return productDTO;
    };
}
