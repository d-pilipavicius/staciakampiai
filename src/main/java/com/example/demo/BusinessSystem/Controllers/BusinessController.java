package com.example.demo.BusinessSystem.Controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BusinessSystem.DTOs.BusinessDTO;
import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.DTOs.UpdateBusinessDTO;
import com.example.demo.BusinessSystem.Entities.Business;
import com.example.demo.BusinessSystem.Mappers.Interfaces.IBusinessMapper;
import com.example.demo.BusinessSystem.Services.Interfaces.IBusinessService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/v1/business")
@AllArgsConstructor
public class BusinessController {
  private IBusinessService service;
  private IBusinessMapper mapper;

  @PostMapping
  public ResponseEntity<BusinessDTO> createBusiness(@Valid @RequestBody CreateBusinessDTO createBusinessDTO) {
    Business business = service.createBusiness(createBusinessDTO);
    BusinessDTO businessDTO = mapper.businessToBusinessDTO(business);
    return ResponseEntity.status(HttpStatus.CREATED).body(businessDTO);
  }

  @DeleteMapping("/{businessId}")
  public ResponseEntity<Void> deleteBusiness(@PathVariable("businessId") UUID businessId) {
      try {
          service.deleteBusiness(businessId);
          return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
      } catch (IllegalArgumentException ex) {
          return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build(); 
      }
  }

  @PutMapping("/{businessId}")
  public ResponseEntity<BusinessDTO> updateBusiness(@PathVariable UUID businessId, @Valid @RequestBody UpdateBusinessDTO updateBusinessDTO) {
    Business updatedBusiness = service.updateBusiness(businessId, updateBusinessDTO);
    BusinessDTO businessDTO = mapper.businessToBusinessDTO(updatedBusiness);
    return ResponseEntity.status(HttpStatus.OK).body(businessDTO);
  }
  
  
}
