package com.example.demo.BusinessComponent.API.Endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.ApplicationServices.BusinessApplicationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/business")
@AllArgsConstructor
public class BusinessController {
  private final BusinessApplicationService service;

  @PostMapping
  public ResponseEntity<BusinessDTO> createBusiness(@Valid @RequestBody CreateBusinessDTO createBusinessDTO) {
    BusinessDTO businessDTO = service.createBusiness(createBusinessDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(businessDTO);
  }
}
