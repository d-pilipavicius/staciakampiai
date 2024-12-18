package com.example.demo.taxComponent.helper.mapper;

import com.example.demo.taxComponent.api.dtos.GetTaxesDTO;
import com.example.demo.taxComponent.api.dtos.PutTaxDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.example.demo.taxComponent.api.dtos.PostTaxDTO;
import com.example.demo.taxComponent.api.dtos.TaxDTO;
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
                .productIds(postTaxDTO.getEntitledProductIds())
                .build();
    }

    public GetTaxesDTO toGetTaxesDTO(Page<TaxDTO> page){
        return GetTaxesDTO.builder()
                .totalPages(page.getTotalPages())
                .totalItems((int) page.getTotalElements())
                .currentPage(page.getNumber())
                .items(page.getContent())
                .build();
    }

    public Tax fromPutToTax(PutTaxDTO putTaxDTO, Tax original){
        original.setTitle(putTaxDTO.getTitle());
        original.setRatePercentage(putTaxDTO.getRatePercentage());
        original.setProductIds(putTaxDTO.getEntitledProductIds());
        return original;
    }
}
