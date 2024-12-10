package com.example.demo.taxComponent.helper.mapper;

import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.taxComponent.api.dtos.PostTaxDTO;
import com.example.demo.taxComponent.api.dtos.TaxHelperDTOs.TaxDTO;
import com.example.demo.taxComponent.domain.entities.Tax;

import java.util.List;
import java.util.Optional;

public class TaxMapper {

    public static final StaticMapper<PostTaxDTO, Tax> TO_MODEL = dto -> {
        if (dto == null) throw new IllegalArgumentException("PostTaxDTO is null");
        Tax tax = new Tax();
        tax.setTitle(dto.getTitle());
        tax.setRatePercentage(dto.getRatePercentage());
        tax.setBusinessId(dto.getBusinessId());
        return tax;
    };

    public static final StaticMapper<Tax, TaxDTO> TO_DTO = entity -> {
        if (entity == null) throw new IllegalArgumentException("Tax is null");
        return new TaxDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getRatePercentage(),
                entity.getBusinessId()
        );
    };
    

    
    public static List<Tax> mapToModelList(List<PostTaxDTO> dtos) {
        return Mapper.mapToModelList(dtos, TO_MODEL);
    }

    public static List<TaxDTO> mapToDTOList(List<Tax> entities) {
        return Mapper.mapToDTOList(entities, TO_DTO);
    }

    public static Optional<Tax> mapToModelOptional(PostTaxDTO dto) {
        return Mapper.mapToModelOptional(dto, TO_MODEL);
    }

    public static Optional<TaxDTO> mapToDTOOptional(Tax entity) {
        return Mapper.mapToDTOOptional(entity, TO_DTO);
    }
}
