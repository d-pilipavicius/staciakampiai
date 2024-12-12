package com.example.demo.taxComponent.domain.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.taxComponent.api.dtos.GetTaxesDTO;
import com.example.demo.taxComponent.api.dtos.PostTaxDTO;
import com.example.demo.taxComponent.api.dtos.PutTaxDTO;
import com.example.demo.taxComponent.api.dtos.TaxHelperDTOs.TaxDTO;
import com.example.demo.taxComponent.domain.entities.Tax;
import com.example.demo.taxComponent.helper.mapper.TaxMapper;
import com.example.demo.taxComponent.repository.TaxRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaxService {
    private final TaxRepository taxRepository;
    private final TaxMapper taxMapper;

    public TaxDTO createTax(PostTaxDTO postTaxDTO) {
        Tax tax = taxMapper.toTax(postTaxDTO);
        Tax savedTax = taxRepository.save(tax);
        return taxMapper.toTaxDTO(savedTax);
    }

    public GetTaxesDTO getAllTaxes(int page, int size) {   
        Pageable pageable = PageRequest.of(page, size);
        List<Tax> taxEntities = taxRepository.findAll(pageable).getContent();

        List<TaxDTO> taxDTOs = taxEntities.stream()
                .map(taxMapper::toTaxDTO)  
                .collect(Collectors.toList());
        
        return new GetTaxesDTO(
                taxDTOs.size(),   
                size,              
                page,              
                taxDTOs           
        );
    }

    public TaxDTO updateTax(PutTaxDTO putTaxDTO, UUID id) {
        Tax tax = taxRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tax with id " + id + " not found"));
        applyTaxUpdates(putTaxDTO, tax);
        Tax updatedTax = taxRepository.save(tax);    
        return taxMapper.toTaxDTO(updatedTax);
    }

    private void applyTaxUpdates(PutTaxDTO putTaxDTO, Tax tax) {
        putTaxDTO.getTitle().ifPresent(tax::setTitle);
        putTaxDTO.getRatePercentage().ifPresent(tax::setRatePercentage);
    }

    public void deleteTax(UUID taxId) {
        if (taxRepository.existsById(taxId)) {
            taxRepository.deleteById(taxId);
        }else{
            throw new IllegalArgumentException("Tax with id " + taxId + " not found");
        }
    }
}
