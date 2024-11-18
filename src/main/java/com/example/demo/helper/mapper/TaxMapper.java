package com.example.demo.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.orders.api.DTOs.TaxDTOs.PostTaxDTO;
import com.example.demo.orders.api.DTOs.TaxDTOs.TaxDTOsObjects.FullTax;
import com.example.demo.orders.domain.entities.Tax;

public class TaxMapper {

    public static final StaticMapper<PostTaxDTO, Tax> TO_MODEL = dto -> {
        Tax tax = new Tax();
        tax.setTitle(dto.getTitle());
        tax.setRatePercentage(dto.getRatePercentage());
        tax.setBusinessId(dto.getBusinessId());
        return tax;
    };

    public static final StaticMapper<Tax, FullTax> TO_DTO = entity -> {
        FullTax taxDTO = new FullTax();
        taxDTO.setTitle(entity.getTitle());
        taxDTO.setRatePercentage(entity.getRatePercentage());
        taxDTO.setBusinessId(entity.getBusinessId());
        return taxDTO;
    };
}
