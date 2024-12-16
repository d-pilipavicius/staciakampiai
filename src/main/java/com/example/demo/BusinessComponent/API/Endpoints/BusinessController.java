package com.example.demo.BusinessComponent.API.Endpoints;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.GetBusinessListDTO;
import com.example.demo.BusinessComponent.API.DTOs.UpdateBusinessDTO;
import com.example.demo.BusinessComponent.ApplicationServices.BusinessApplicationService;
import com.example.demo.CommonDTOs.PageinationDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1/business")
@AllArgsConstructor
public class BusinessController {
  private final BusinessApplicationService businessApplicationService;

  @PostMapping
  public ResponseEntity<BusinessDTO> createBusiness(@Valid @NotNull @RequestBody CreateBusinessDTO createBusinessDTO) {
    BusinessDTO businessDTO = businessApplicationService.createBusiness(createBusinessDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(businessDTO);
  }

  @GetMapping
  public ResponseEntity<GetBusinessListDTO> getBusinessList(
      @Valid @NotNull @ModelAttribute PageinationDTO pageinationInfo) {
    GetBusinessListDTO getBusinessListDTO = businessApplicationService.getBusinessList(pageinationInfo);
    return ResponseEntity.ok(getBusinessListDTO);
  }

  @GetMapping("/{businessId}")
  public ResponseEntity<BusinessDTO> getBusiness(@NotNull @PathVariable UUID businessId) {
    BusinessDTO businessDTO = businessApplicationService.getBusiness(businessId);
    return ResponseEntity.ok().body(businessDTO);
  }

  @PutMapping("/{businessId}")
  public ResponseEntity<BusinessDTO> updateBusiness(@NotNull @PathVariable UUID businessId,
      @NotNull @Valid @RequestBody UpdateBusinessDTO updateBusinessDTO) {
    BusinessDTO businessDTO = businessApplicationService.updateBusiness(businessId, updateBusinessDTO);
    return ResponseEntity.status(HttpStatus.OK).body(businessDTO);
  }

  @DeleteMapping("/{businessId}")
  public ResponseEntity<Void> deleteBusiness(@NotNull @PathVariable UUID businessId) {
    try {
      businessApplicationService.deleteBusiness(businessId);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (IllegalArgumentException ex) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }
  }
}
