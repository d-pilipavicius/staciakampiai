package com.example.demo.discountComponent.helper.mapper;

import com.example.demo.discountComponent.api.dtos.GetDiscountsDTO;
import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.discountComponent.api.dtos.DiscountDTO;
import com.example.demo.discountComponent.api.dtos.PostDiscountDTO;
import com.example.demo.discountComponent.domain.entities.Discount;
import org.springframework.data.domain.Page;

public class DiscountMapper {

    public static final StaticMapper<PostDiscountDTO, Discount> TO_DiscountModel = dto -> Discount
            .builder()
            .businessId(dto.getBusinessId())
            .productIds(dto.getEntitledProductIds())
            .code(dto.getCode())
            .amount(dto.getAmount())
            .valueType(dto.getValueType())
            .currency(dto.getCurrency())
            .target(dto.getTarget())
            .validFrom(dto.getValidFrom())
            .validUntil(dto.getValidUntil())
            .usageCountLimit(dto.getUsageCountLimit())
            .build();

    public static final StaticMapper<Discount, DiscountDTO> TO_DiscountDTO = entity -> DiscountDTO
            .builder()
            .id(entity.getId())
            .businessId(entity.getBusinessId())
            .entitledProductIds(entity.getProductIds())
            .code(entity.getCode())
            .amount(entity.getAmount())
            .valueType(entity.getValueType())
            .currency(entity.getCurrency())
            .target(entity.getTarget())
            .validFrom(entity.getValidFrom())
            .validUntil(entity.getValidUntil())
            .usageCountLimit(entity.getUsageCountLimit())
            .usageCount(entity.getUsageCount())
            .build();

    public static final StaticMapper<Page<DiscountDTO>, GetDiscountsDTO> TO_GetDiscountsModel = page -> GetDiscountsDTO
            .builder()
            .totalPages(page.getTotalPages())
            .totalItems((int) page.getTotalElements())
            .currentPage(page.getNumber())
            .items(page.getContent())
            .build();
}
