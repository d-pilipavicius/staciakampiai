package com.example.demo.BusinessComponent.Domain.Services;

import java.util.UUID;

import com.example.demo.BusinessComponent.validator.BusinessValidator;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
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
  private final BusinessValidator businessValidator;

  public BusinessDTO createBusiness(@NotNull @Valid CreateBusinessDTO dto) {
    businessValidator.checkIfPhoneNumberValid(dto.getPhoneNumber());
    Business business = businessMapper.toBusiness(dto);
    Business savedBusiness = businessRepository.save(business);
    return businessMapper.toBusinessDTO(savedBusiness);
  }

  public GetBusinessListDTO getBusinessList(@NotNull @Valid PageinationDTO pageinationInfo) {
    Pageable pageable = PageRequest.of(pageinationInfo.getPage(), pageinationInfo.getPageSize());
    Page<Business> pageination = businessRepository.findAll(pageable);
    businessValidator.checkIfAnyBusinessExisted((int) pageination.getTotalElements());
    businessValidator.checkIfBusinessPageExceeded(pageinationInfo.getPage(), pageination.getTotalPages());
    return businessMapper.toGetBusinessListDTO(pageination);
  }

  public BusinessDTO getBusiness(@NotNull @Valid UUID businessId) {
    businessValidator.checkIfBusinessIdExists(businessId);
    Business business = businessRepository.getReferenceById(businessId);
    return businessMapper.toBusinessDTO(business);
  }

  public BusinessDTO updateBusiness(@NotNull UUID businessId, @NotNull @Valid UpdateBusinessDTO updateBusinessDTO) {
    Business existingBusiness = businessRepository.findById(businessId)
        .orElseThrow(() -> new NotFoundException("Business not found with ID: " + businessId));

    businessValidator.checkIfPhoneNumberValid(updateBusinessDTO.getPhoneNumber());

    Business updatedBusiness = businessMapper.updateBusinessFromDto(updateBusinessDTO, existingBusiness);
    Business savedBusiness = businessRepository.save(updatedBusiness);
    return businessMapper.toBusinessDTO(savedBusiness);
  }

  public void deleteBusiness(@NotNull UUID businessId) {
    businessValidator.checkIfBusinessIdExists(businessId);
    businessRepository.deleteById(businessId);
  }
}
