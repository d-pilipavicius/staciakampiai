package com.example.demo.taxComponent.api.endpoints;

import com.example.demo.taxComponent.api.dtos.GetTaxesDTO;
import com.example.demo.taxComponent.api.dtos.PutTaxDTO;
import com.example.demo.taxComponent.api.dtos.PostTaxDTO;
import com.example.demo.taxComponent.api.dtos.ResponseTaxDTO;
import com.example.demo.taxComponent.api.dtos.TaxHelperDTOs.TaxDTO;
import com.example.demo.taxComponent.applicationServices.TaxApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;



@RestController
@RequestMapping("/v1/taxes")
public class TaxesController {

    private TaxApplicationService taxApplicationService;

    @Autowired
    public TaxesController(TaxApplicationService taxApplicationService){
        this.taxApplicationService = taxApplicationService;
    }

    @PostMapping
    public ResponseEntity<Object> createTax (@Valid @RequestBody PostTaxDTO postTaxDTO){
        TaxDTO createdTax = taxApplicationService.createTax(postTaxDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTax);
    }

    @PutMapping("{taxId}")
    public ResponseEntity<Object> putMethodName(@PathVariable UUID taxId, @Valid @RequestBody PutTaxDTO putTaxDTO) {
        ResponseTaxDTO updatedTax = taxApplicationService.updateTax(putTaxDTO, taxId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTax);
    }

    @DeleteMapping("{taxId}")
    public ResponseEntity<Object> deleteTax(@PathVariable UUID taxId){
        taxApplicationService.deleteTax(taxId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Object> getTaxes(@RequestParam int pageNumber, @RequestParam int pageSize){
        GetTaxesDTO taxes = taxApplicationService.getAllTaxes(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(taxes);
    }
    


}
