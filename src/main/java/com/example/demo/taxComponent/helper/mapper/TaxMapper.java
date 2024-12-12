package com.example.demo.taxComponent.helper.mapper;

import org.springframework.stereotype.Component;
import com.example.demo.taxComponent.api.dtos.PostTaxDTO;
import com.example.demo.taxComponent.api.dtos.TaxHelperDTOs.TaxDTO;
import com.example.demo.taxComponent.domain.entities.Tax;

@Component
public class TaxMapper {

    public TaxDTO toTaxDTO(Tax tax) {
        if (tax == null) {
            throw new IllegalArgumentException("Tax entity cannot be null");
        }

        return TaxDTO.builder()
                .id(tax.getId())
                .title(tax.getTitle())
                .ratePercentage(tax.getRatePercentage())
                .businessId(tax.getBusinessId())
                .build();
    }

    public Tax toTax(PostTaxDTO postTaxDTO) {
        if (postTaxDTO == null) {
            throw new IllegalArgumentException("PostTaxDTO cannot be null");
        }

        return Tax.builder()
                .title(postTaxDTO.getTitle())
                .ratePercentage(postTaxDTO.getRatePercentage())
                .businessId(postTaxDTO.getBusinessId())
                .build();
    }
}
