package com.example.demo.taxComponent.applicationServices;

import com.example.demo.taxComponent.api.dtos.GetTaxesDTO;
import com.example.demo.taxComponent.api.dtos.PutTaxDTO;
import com.example.demo.taxComponent.api.dtos.PostTaxDTO;
import com.example.demo.taxComponent.api.dtos.TaxHelperDTOs.TaxDTO;
import com.example.demo.taxComponent.domain.services.TaxService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.UUID;

@AllArgsConstructor
@Service
public class TaxApplicationService {

    private final TaxService taxService;

    public TaxDTO createTax(PostTaxDTO postTaxDTO) {
        return taxService.createTax(postTaxDTO);
    }

    public GetTaxesDTO getAllTaxes(int page, int size) {
        return taxService.getAllTaxes(page, size);
    }

    public TaxDTO updateTax(PutTaxDTO patchTaxDTO, UUID id) {
        return taxService.updateTax(patchTaxDTO, id);
    }

    public void deleteTax(UUID id) {
        taxService.deleteTax(id);
    }
}
