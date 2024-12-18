package com.example.demo.taxComponent.applicationServices;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.taxComponent.api.dtos.GetTaxesDTO;
import com.example.demo.taxComponent.api.dtos.PutTaxDTO;
import com.example.demo.taxComponent.api.dtos.PostTaxDTO;
import com.example.demo.taxComponent.api.dtos.TaxDTO;
import com.example.demo.taxComponent.domain.services.TaxService;
import com.example.demo.productComponent.applicationServices.ProductApplicationService;

import com.example.demo.taxComponent.repository.TaxRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TaxApplicationService {

    private final TaxService taxService;

    private final ProductApplicationService productApplicationService;

    private final TaxRepository taxRepository;

    @Transactional
    public TaxDTO createTax(PostTaxDTO postTaxDTO) {
        if(!productApplicationService.validateProductIds(postTaxDTO.getEntitledProductIds())){
            throw new NotFoundException("The provided product ids were not found.");
        }
        TaxDTO createdTax = taxService.createTax(postTaxDTO);
        List<ProductDTO> productsEntitled = productApplicationService.getProductsByListOfId(postTaxDTO.getEntitledProductIds());
        createdTax.setEntitledProducts(productsEntitled);
        return createdTax;
    }

    @Transactional
    public GetTaxesDTO getAllTaxes(int page, int size, UUID businessId) {
        GetTaxesDTO getTaxesDTO = taxService.getAllTaxes(page, size, businessId);
        getTaxesDTO.getItems().forEach(taxDTO -> {
            List<UUID> proudctIds = taxRepository.findProductsByTaxId(taxDTO.getId());
            taxDTO.setEntitledProducts(productApplicationService.getProductsByListOfId(proudctIds));
        });

        return  getTaxesDTO;
    }

    @Transactional
    public TaxDTO updateTax(PutTaxDTO putTaxDTO, UUID id) {
        if(!productApplicationService.validateProductIds(putTaxDTO.getEntitledProductIds())){
            throw new NotFoundException("The provided product ids were not found.");
        }

        TaxDTO updatedtaxDTO = taxService.updateTax(putTaxDTO, id);
        List<ProductDTO> productsEntitled = productApplicationService.getProductsByListOfId(putTaxDTO.getEntitledProductIds());
        updatedtaxDTO.setEntitledProducts(productsEntitled);
        return updatedtaxDTO;
    }

    public void deleteTax(UUID id) {
        taxService.deleteTax(id);
    }
}
