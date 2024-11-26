package com.example.demo.BusinessComponent.Domain.Services;

import org.springframework.stereotype.Service;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.BusinessComponent.Domain.Services.Interfaces.IBusinessService;
import com.example.demo.BusinessComponent.Helpers.Mappers.Interfaces.IBusinessMapper;
import com.example.demo.BusinessComponent.Repositories.IBusinessRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BusinessService implements IBusinessService{
  private final IBusinessRepository repository;
  private final IBusinessMapper mapper;

  @Override
  public BusinessDTO createBusiness(CreateBusinessDTO dto) {
    Business business = mapper.createBusinessDTOToBusiness(dto);
    Business savedBusiness = repository.save(business); 
    return mapper.businessToBusinessDTO(savedBusiness);
  }

}
