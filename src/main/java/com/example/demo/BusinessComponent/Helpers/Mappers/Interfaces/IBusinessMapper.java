package com.example.demo.BusinessComponent.Helpers.Mappers.Interfaces;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.Domain.Entities.Business;

public interface IBusinessMapper {
  public BusinessDTO businessToBusinessDTO(Business business);
  public Business createBusinessDTOToBusiness(CreateBusinessDTO createBusinessDTO);
}
