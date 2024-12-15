package com.example.demo.BusinessComponent.Domain.Services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.GetBusinessListDTO;
import com.example.demo.BusinessComponent.API.DTOs.UpdateBusinessDTO;
import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.BusinessComponent.Helpers.Mappers.BusinessMapper;
import com.example.demo.BusinessComponent.Repositories.IBusinessRepository;
import com.example.demo.CommonDTOs.PageinationDTO;
import com.example.demo.UserComponent.Repositories.IUserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
// TODO: Add exception handling
public class BusinessService {
  private final IBusinessRepository businessRepository;
  private final IUserRepository userRepository;
  private final BusinessMapper businessMapper;

  public BusinessDTO createBusiness(@NotNull @Valid CreateBusinessDTO dto) {
    Business business = businessMapper.toBusiness(dto);
    Business savedBusiness = businessRepository.save(business);
    return businessMapper.toBusinessDTO(savedBusiness);
  }

  public GetBusinessListDTO getBusinessList(@NotNull @Valid PageinationDTO pageinationInfo) {
    // Set up pageable entity that is used for retrieving the specific business
    // items from the database
    Pageable pageable = PageRequest.of(pageinationInfo.getPage(), pageinationInfo.getPageSize());
    Page<Business> pageination = businessRepository.findAll(pageable);
    return businessMapper.toGetBusinessListDTO(pageination);
  }

  public BusinessDTO getBusiness(@NotNull @Valid UUID businessId) {
    Business business = businessRepository.getReferenceById(businessId);
    return businessMapper.toBusinessDTO(business);
  }

  public BusinessDTO updateBusiness(@NotNull UUID businessId, @NotNull @Valid UpdateBusinessDTO updateBusinessDTO) {
    // TODO: Move exception handling to a different class
    Business existingBusiness = businessRepository.findById(businessId)
        .orElseThrow(() -> new EntityNotFoundException("Business not found with ID: " + businessId));

    if (updateBusinessDTO.getOwnerId() != null) {
      boolean ownerExists = userRepository.existsById(updateBusinessDTO.getOwnerId());
      if (!ownerExists) {
        throw new EntityNotFoundException("No user found with ID: " + updateBusinessDTO.getOwnerId());
      }
    }

    Business updatedBusiness = businessMapper.updateBusinessFromDto(updateBusinessDTO, existingBusiness);
    Business savedBusiness = businessRepository.save(updatedBusiness);
    return businessMapper.toBusinessDTO(savedBusiness);
  }

  public void deleteBusiness(@NotNull UUID businessId) {
    // TODO: Move exception handling to a different class
    if (businessRepository.existsById(businessId)) {
      businessRepository.deleteById(businessId);
    } else {
      throw new EntityNotFoundException("Business with ID " + businessId + " does not exist.");
    }
  }
}
