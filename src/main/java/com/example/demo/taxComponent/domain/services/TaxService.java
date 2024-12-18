package com.example.demo.taxComponent.domain.services;

import com.example.demo.productComponent.applicationServices.ProductApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.taxComponent.api.dtos.GetTaxesDTO;
import com.example.demo.taxComponent.api.dtos.PostTaxDTO;
import com.example.demo.taxComponent.api.dtos.PutTaxDTO;
import com.example.demo.taxComponent.api.dtos.TaxDTO;
import com.example.demo.taxComponent.domain.entities.Tax;
import com.example.demo.taxComponent.helper.mapper.TaxMapper;
import com.example.demo.taxComponent.repository.TaxRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaxService {
    private final TaxRepository taxRepository;

    private final ProductApplicationService productApplicationService;
    private final TaxMapper taxMapper;

    public TaxDTO createTax(PostTaxDTO postTaxDTO) {
        Tax tax = taxMapper.toTax(postTaxDTO);
        Tax savedTax = taxRepository.save(tax);
        return taxMapper.toTaxDTO(savedTax);
    }

    public GetTaxesDTO getAllTaxes(int page, int size, UUID businessId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TaxDTO> taxPage = taxRepository.findByBusinessId(businessId, pageable).map(taxMapper::toTaxDTO);

        return GetTaxesDTO.builder()
                .currentPage(taxPage.getNumber())
                .totalItems((int) taxPage.getTotalElements())
                .totalPages(taxPage.getTotalPages())
                .items(taxPage.getContent())
                .build();
    }

    @Transactional
    public TaxDTO updateTax(PutTaxDTO putTaxDTO, UUID id) {
        Tax tax = taxRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tax with id " + id + " not found"));
        Tax updatedTax = taxMapper.fromPutToTax(putTaxDTO, tax);
        Tax savedTax = taxRepository.save(updatedTax);
        return taxMapper.toTaxDTO(savedTax);
    }

    @Transactional
    public void deleteTax(UUID taxId) {
        if (taxRepository.existsById(taxId)) {
            taxRepository.deleteById(taxId);
        }else{
            throw new IllegalArgumentException("Tax with id " + taxId + " not found");
        }
    }
}
