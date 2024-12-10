package com.example.demo.taxComponent.applicationServices;

import com.example.demo.taxComponent.api.dtos.GetTaxesDTO;
import com.example.demo.taxComponent.api.dtos.PutTaxDTO;
import com.example.demo.taxComponent.api.dtos.PostTaxDTO;
import com.example.demo.taxComponent.api.dtos.TaxHelperDTOs.TaxDTO;
import com.example.demo.taxComponent.domain.services.TaxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TaxApplicationService {

    private final TaxService taxService;

    public TaxApplicationService(TaxService taxService) {
        this.taxService = taxService;
    }

    @Transactional
    public TaxDTO createTax(PostTaxDTO postTaxDTO) {
        return taxService.createTax(postTaxDTO);
    }

    public GetTaxesDTO getAllTaxes(int page, int size) {
        return taxService.getAllTaxes(page, size);
    }

    @Transactional
    public TaxDTO updateTax(PutTaxDTO patchTaxDTO, UUID id) {
        return taxService.updateTax(patchTaxDTO, id);
    }

    @Transactional
    public void deleteTax(UUID id) {
        taxService.deleteTax(id);
    }
}
