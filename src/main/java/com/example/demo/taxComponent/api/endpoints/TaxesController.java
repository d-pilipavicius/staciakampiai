package com.example.demo.taxComponent.api.endpoints;

import com.example.demo.taxComponent.api.dtos.GetTaxesDTO;
import com.example.demo.taxComponent.api.dtos.PutTaxDTO;
import com.example.demo.taxComponent.api.dtos.PostTaxDTO;
import com.example.demo.taxComponent.api.dtos.TaxHelperDTOs.TaxDTO;
import com.example.demo.taxComponent.applicationServices.TaxApplicationService;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;



@RestController
@AllArgsConstructor
@RequestMapping("/v1/taxes/{businessId}")
public class TaxesController {

    private final TaxApplicationService taxApplicationService;

    @PostMapping
    public ResponseEntity<Object> createTax (@NotNull @Valid @RequestBody PostTaxDTO postTaxDTO){
        TaxDTO createdTax = taxApplicationService.createTax(postTaxDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTax);
    }

    @PutMapping("{taxId}")
    public ResponseEntity<Object> putMethodName(@NotNull @PathVariable UUID taxId, @NotNull @Valid @RequestBody PutTaxDTO putTaxDTO) {
        TaxDTO updatedTax = taxApplicationService.updateTax(putTaxDTO, taxId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTax);
    }

    @DeleteMapping("{taxId}")
    public ResponseEntity<Object> deleteTax(@NotNull @PathVariable UUID taxId){
        taxApplicationService.deleteTax(taxId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Object> getTaxes(@NotNull @RequestParam int pageNumber, @NotNull @RequestParam int pageSize){
        GetTaxesDTO taxes = taxApplicationService.getAllTaxes(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(taxes);
    }
    
}
