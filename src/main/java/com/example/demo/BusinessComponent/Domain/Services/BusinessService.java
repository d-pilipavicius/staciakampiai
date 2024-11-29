package com.example.demo.BusinessComponent.Domain.Services;

import org.springframework.stereotype.Service;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.BusinessComponent.Helpers.Mappers.BusinessMapper;
import com.example.demo.BusinessComponent.Repositories.IBusinessRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BusinessService {
  private final IBusinessRepository businessRepository;
  private final BusinessMapper businessMapper;

  public BusinessDTO createBusiness(CreateBusinessDTO dto) {
    Business business = businessMapper.toBusiness(dto);
    Business savedBusiness = businessRepository.save(business); 
    return businessMapper.toBusinessDTO(savedBusiness);
  }

}
