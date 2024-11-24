package com.example.demo.BusinessSystem.Services;

import org.springframework.stereotype.Service;

import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.Entities.Business;
import com.example.demo.BusinessSystem.Mappers.Interfaces.IBusinessMapper;
import com.example.demo.BusinessSystem.Repositories.IBusinessRepository;
import com.example.demo.BusinessSystem.Services.Interfaces.IBusinessService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BusinessService implements IBusinessService{
  private IBusinessRepository repository;
  private IBusinessMapper mapper;

  @Override
  public Business createBusiness(CreateBusinessDTO dto) {
    Business business = mapper.createBusinessDTOToBusiness(dto);
    return repository.save(business); 
  }

}
