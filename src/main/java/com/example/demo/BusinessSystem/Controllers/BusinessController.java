package com.example.demo.BusinessSystem.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BusinessSystem.DTOs.BusinessDTO;
import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.Entities.Business;
import com.example.demo.BusinessSystem.Mappers.Interfaces.IBusinessMapper;
import com.example.demo.BusinessSystem.Services.Interfaces.IBusinessService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

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
}
