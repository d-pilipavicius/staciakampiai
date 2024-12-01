package com.example.demo.BusinessComponent.Domain.Services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.GetBusinessListDTO;
import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.BusinessComponent.Helpers.Mappers.BusinessMapper;
import com.example.demo.BusinessComponent.Repositories.IBusinessRepository;
import com.example.demo.CommonDTOs.PageinationDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BusinessService {
  private final IBusinessRepository businessRepository;
  private final BusinessMapper businessMapper;

  public BusinessDTO createBusiness(@NotNull @Valid CreateBusinessDTO dto) {
    Business business = businessMapper.toBusiness(dto);
    Business savedBusiness = businessRepository.save(business); 
    return businessMapper.toBusinessDTO(savedBusiness);
  }

  public GetBusinessListDTO getBusinessList(@NotNull @Valid PageinationDTO pageinationInfo) {
    //Set up pageable entity that is used for retrieving the specific business items from the database
    Pageable pageable = PageRequest.of(pageinationInfo.getPage(), pageinationInfo.getPageSize());
    Page<Business> pageination = businessRepository.findAll(pageable);
    return businessMapper.toGetBusinessListDTO(pageination);
  }

  public BusinessDTO getBusiness(@NotNull @Valid UUID businessId) {
    Business business = businessRepository.getReferenceById(businessId);
    return businessMapper.toBusinessDTO(business);
  }
}
