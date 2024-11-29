package com.example.demo.BusinessSystem.Controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BusinessSystem.DTOs.BusinessDTO;
import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.DTOs.GetBusinessListDTO;
import com.example.demo.BusinessSystem.Entities.Business;
import com.example.demo.BusinessSystem.Mappers.Interfaces.IBusinessMapper;
import com.example.demo.BusinessSystem.Services.Interfaces.IBusinessService;
import com.example.demo.CommonDTOs.PageinationDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/v1/business")
@AllArgsConstructor
public class BusinessController {
  @NotNull
  private final IBusinessService service;
  @NotNull
  private final IBusinessMapper mapper;

  @PostMapping
  public ResponseEntity<BusinessDTO> createBusiness(@Valid @RequestBody CreateBusinessDTO createBusinessDTO) {
    Business business = service.createBusiness(createBusinessDTO);
    BusinessDTO businessDTO = mapper.businessToBusinessDTO(business);
    return ResponseEntity.status(HttpStatus.CREATED).body(businessDTO);
  }

  @GetMapping
  public ResponseEntity<GetBusinessListDTO> getBusinessList(@ModelAttribute PageinationDTO pageinationInfo) {
    Page<Business> pageinatedBusinessList = service.getBusinessList(pageinationInfo);
    GetBusinessListDTO getBusinessListDTO = mapper.businessPageToGetBusinessListDTO(pageinatedBusinessList);
    return ResponseEntity.ok(getBusinessListDTO);
  }
  
  @GetMapping("/{businessId}")
  public ResponseEntity<BusinessDTO> getBusiness(@PathVariable UUID businessId) {
    BusinessDTO businessDTO = mapper.businessToBusinessDTO(service.getBusiness(businessId));
    return ResponseEntity.ok().body(businessDTO);
  }
}
