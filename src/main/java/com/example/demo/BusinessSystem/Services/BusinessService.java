package com.example.demo.BusinessSystem.Services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.Entities.Business;
import com.example.demo.BusinessSystem.Mappers.Interfaces.IBusinessMapper;
import com.example.demo.BusinessSystem.Repositories.IBusinessRepository;
import com.example.demo.BusinessSystem.Services.Interfaces.IBusinessService;
import com.example.demo.CommonDTOs.PageinationDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BusinessService implements IBusinessService{
  @NotNull
  private final IBusinessRepository repository;
  @NotNull
  private final IBusinessMapper mapper;

  @Override
  public Business createBusiness(CreateBusinessDTO dto) {
    Business business = mapper.createBusinessDTOToBusiness(dto);
    return repository.save(business);
  }

  @Override
  public Page<Business> getBusinessList(PageinationDTO pageinationInfo) {
    //Set up pageable entity that is used for retrieving the specific business items from the database
    Pageable pageable = PageRequest.of(pageinationInfo.getPage(), pageinationInfo.getPageSize());
    return repository.findAll(pageable);
  }

  @Override
  public Business getBusiness(UUID businessId) {
    return repository.getReferenceById(businessId);
  }

}
