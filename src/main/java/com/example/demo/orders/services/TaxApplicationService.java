package com.example.demo.orders.services;

import com.example.demo.orders.API.DTOs.TaxDTOs.GetTaxesDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.PatchTaxDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.PostTaxDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.ResponseTaxDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.TaxHelperDTOs.TaxDTO;
import com.example.demo.orders.domain.services.TaxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TaxApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(TaxApplicationService.class);

    private final TaxService taxService;

    public TaxApplicationService(TaxService taxService) {
        this.taxService = taxService;
    }

    @Transactional
    public TaxDTO createTax(PostTaxDTO postTaxDTO) {
        return taxService.createTax(postTaxDTO);
    }

    public GetTaxesDTO getAllTaxes() {
        return taxService.getAllTaxes();
    }

    public GetTaxesDTO getAllTaxes(int page, int size) {
        return taxService.getAllTaxes(page, size);
    }

    @Transactional
    public ResponseTaxDTO updateTax(PatchTaxDTO patchTaxDTO, UUID id) {
        return taxService.updateTax(patchTaxDTO, id);
    }

    @Transactional
    public void deleteTax(UUID id) {
        taxService.deleteTax(id);
    }
}
