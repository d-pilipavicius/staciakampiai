package com.example.demo.orders.domain.services;

import com.example.demo.helper.mapper.TaxMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.orders.API.DTOs.TaxDTOs.GetTaxesDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.PatchTaxDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.ResponseTaxDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.PostTaxDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.TaxHelperDTOs.TaxDTO;
import com.example.demo.orders.domain.entities.Tax;
import com.example.demo.orders.repository.TaxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaxService {

    private static final Logger logger = LoggerFactory.getLogger(TaxService.class);

    private final TaxRepository taxRepository;

    public TaxService(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

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

        // TODO: add constructor to GetTaxesDTO
        GetTaxesDTO getTaxesDTO = new GetTaxesDTO();
        getTaxesDTO.setItems(taxes);

        return getTaxesDTO;
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
        Optional<Tax> tax = taxRepository.findById(id);

        if(tax.isEmpty()){
            logger.error("Tax with id {} not found", id);
            return null;
        }

        patchTaxDTO.getTitle().ifPresent(tax.get()::setTitle);
        patchTaxDTO.getRatePercentage().ifPresent(tax.get()::setRatePercentage);

        ResponseTaxDTO newTaxDTO = new ResponseTaxDTO();
        newTaxDTO.setTax(Mapper.mapToDTO(
                taxRepository.save(tax.get()),
                TaxMapper.TO_DTO
        ));

        return newTaxDTO;
    }

    public void deleteTax(UUID id){
        taxRepository.deleteById(id);
    }
}
