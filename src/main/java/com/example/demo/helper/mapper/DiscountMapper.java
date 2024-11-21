package com.example.demo.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.orders.API.DTOs.DiscountDTO.DiscountHelperDTOs.DiscountDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.PostDiscountDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.PostTaxDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.TaxHelperDTOs.TaxDTO;
import com.example.demo.orders.domain.entities.Discount;
import com.example.demo.orders.domain.entities.Tax;
import com.example.demo.orders.domain.entities.enums.Currency;

import java.util.Optional;

public class DiscountMapper {

    public static final StaticMapper<PostDiscountDTO, Discount> TO_MODEL = dto -> {
        Discount discount = new Discount();
        discount.setBusinessId(dto.getBusinessId());
        discount.setProductIds(dto.getEntitledProductIds().orElse(null));
        discount.setCode(dto.getCode().orElse(""));
        discount.setValue(dto.getValue());
        discount.setValueType(dto.getValueType());
        discount.setCurrency(dto.getCurrency().orElse(Currency.EUR));
        discount.setTarget(dto.getTarget());
        discount.setValidFrom(dto.getValidFrom());
        discount.setValidUntil(dto.getValidUntil());
        discount.setUsageCountLimit(dto.getUsageCountLimit().orElse(null));
        return discount;
    };

    public static final StaticMapper<Discount, DiscountDTO> TO_DTO = entity -> {
        DiscountDTO dto = new DiscountDTO();
        dto.setId(entity.getId());
        dto.setBusinessId(entity.getBusinessId());
        dto.setEntitledProductIds(Optional.ofNullable(entity.getProductIds()));
        dto.setCode(Optional.ofNullable(entity.getCode()));
        dto.setValue(entity.getValue());
        dto.setValueType(entity.getValueType());
        dto.setCurrency(Optional.ofNullable(entity.getCurrency()));
        dto.setTarget(entity.getTarget());
        dto.setValidFrom(entity.getValidFrom());
        dto.setValidUntil(entity.getValidUntil());
        dto.setUsageCountLimit(Optional.of(entity.getUsageCountLimit()));
        return dto;
    };
}
