package com.example.demo.taxComponent.domain.services;

import com.example.demo.taxComponent.helper.mapper.TaxMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.taxComponent.api.dtos.GetTaxesDTO;
import com.example.demo.taxComponent.api.dtos.PatchTaxDTO;
import com.example.demo.taxComponent.api.dtos.ResponseTaxDTO;
import com.example.demo.taxComponent.api.dtos.PostTaxDTO;
import com.example.demo.taxComponent.api.dtos.TaxHelperDTOs.TaxDTO;
import com.example.demo.taxComponent.domain.entities.Tax;
import com.example.demo.taxComponent.repository.TaxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaxService {

    private static final Logger logger = LoggerFactory.getLogger(TaxService.class);

    private final TaxRepository taxRepository;

    public TaxService(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    @Transactional
    public TaxDTO createTax(PostTaxDTO postTaxDTO){
        return Mapper.mapToDTO(
                taxRepository.save(
                        Mapper.mapToModel(
                                postTaxDTO,
                                TaxMapper.TO_MODEL
                        )
                ),
                TaxMapper.TO_DTO
        );
    }

    public GetTaxesDTO getAllTaxes(){
        List<TaxDTO> taxes = taxRepository.findAll().stream()
                .map(TaxMapper.TO_DTO::map)
                .collect(Collectors.toList());

        return new GetTaxesDTO(taxes, taxes.size());
    }

    public GetTaxesDTO getAllTaxes(int page, int size){
        List<TaxDTO> taxes = taxRepository.findAll(PageRequest.of(page, size)).stream()
                .map(TaxMapper.TO_DTO::map)
                .collect(Collectors.toList());

        return new GetTaxesDTO(
                taxes.size(),
                size,
                page,
                taxes
        );
    }

    @Transactional
    public ResponseTaxDTO updateTax(PatchTaxDTO patchTaxDTO, UUID id){
        Tax tax = taxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tax with id " + id + " not found"));

        applyTaxUpdates(patchTaxDTO, tax);

        return new ResponseTaxDTO(Mapper.mapToDTO(
                taxRepository.save(tax),
                TaxMapper.TO_DTO
        ));
    }

    public void deleteTax(UUID id){
        if (!taxRepository.existsById(id)) {
            logger.error("Tax with id {} not found", id);
            throw new IllegalArgumentException("Tax with id " + id + " not found");
        }
        taxRepository.deleteById(id);
    }

    private void applyTaxUpdates(PatchTaxDTO patchTaxDTO, Tax tax) {
        patchTaxDTO.getTitle().ifPresent(tax::setTitle);
        patchTaxDTO.getRatePercentage().ifPresent(tax::setRatePercentage);
    }
}
