package com.example.demo.productComponent.helper.mapper;

import com.example.demo.CommonHelper.mapper.base.StaticMapper;
import com.example.demo.productComponent.api.dtos.GetProductsDTO;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.productComponent.domain.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

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
                                                        .build())
                        .businessId(entity.getBusinessId())
                        .build();

        /* method is not usable, because list might be empty
        public static final StaticMapper<Page<ProductDTO>, GetProductsDTO> PAGE_TO_GET_PRODUCTS_DTO = (productDTO) -> GetProductsDTO
                        .builder()
                        .items(productDTO.getContent())
                        .totalItems((int) productDTO.getTotalElements())
                        .totalPages(productDTO.getTotalPages())
                        .currentPage(productDTO.getPageable().getPageNumber())
                        .businessId(productDTO.getContent().get(0).getBusinessId())
                        .build();
        */

        public static GetProductsDTO pageToGetProducts(UUID businessId, Page<ProductDTO> productDTO) {
                return GetProductsDTO
                .builder()
                .items(productDTO.getContent())
                .totalItems((int) productDTO.getTotalElements())
                .totalPages(productDTO.getTotalPages())
                .currentPage(productDTO.getPageable().getPageNumber())
                .businessId(businessId)
                .build();
        }

        public static GetProductsDTO listToGetProductsDTO(UUID businessId, List<ProductDTO> productDTO) {
                return GetProductsDTO
                .builder()
                .items(productDTO)
                .totalItems(productDTO.size())
                .businessId(businessId)
                .build(); 
        }

        /* method uncorrect
        public static final StaticMapper<List<ProductDTO>, GetProductsDTO> LIST_TO_GET_PRODUCTS_DTO = productDTO -> GetProductsDTO
                        .builder()
                        .items(productDTO)
                        .totalItems(productDTO.size())
                        .businessId(productDTO.get(0).getBusinessId())
                        .build(); */
}
