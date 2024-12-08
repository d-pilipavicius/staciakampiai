package com.example.demo.serviceChargeComponent.api.Endpoints;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.serviceChargeComponent.api.dtos.GetServiceChargesDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PostServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PutServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ResponseServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import com.example.demo.serviceChargeComponent.applicationServices.ServiceChargeApplicationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/service-charges")
@AllArgsConstructor
public class ServiceChargesController {
    private final ServiceChargeApplicationService serviceChargeApplicationService;

    @PostMapping
    public ResponseEntity<ServiceChargeDTO> createServiceCharge(@Valid @RequestBody PostServiceChargeDTO postServiceChargeDTO) {
        ServiceChargeDTO serviceChargeDTO = serviceChargeApplicationService.createServiceCharge(postServiceChargeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceChargeDTO);
    }

    @PutMapping("/{serviceChargeId}")
    public ResponseEntity<ResponseServiceChargeDTO> updateServiceCharge(
            @PathVariable UUID serviceChargeId, 
            @Valid @RequestBody PutServiceChargeDTO putServiceChargeDTO) {

        ResponseServiceChargeDTO updatedServiceCharge = serviceChargeApplicationService.updateServiceCharge(putServiceChargeDTO, serviceChargeId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedServiceCharge);
    }

    @GetMapping
    public ResponseEntity<Object> getServiceCharges(@RequestParam int pageNumber, @RequestParam int pageSize){
        GetServiceChargesDTO serviceCharges = serviceChargeApplicationService.getAllServiceCharges(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(serviceCharges);
    }

    @DeleteMapping("{serviceChargeId}")
    public ResponseEntity<Object> deleteServiceCharge(@PathVariable UUID serviceChargeId){
        serviceChargeApplicationService.deleteServiceCharge(serviceChargeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
