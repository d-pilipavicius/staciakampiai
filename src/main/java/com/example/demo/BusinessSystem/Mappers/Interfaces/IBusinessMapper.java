package com.example.demo.BusinessSystem.Mappers.Interfaces;

import com.example.demo.BusinessSystem.DTOs.BusinessDTO;
import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.DTOs.UpdateBusinessDTO;
import com.example.demo.BusinessSystem.Entities.Business;

public interface IBusinessMapper {
  public BusinessDTO businessToBusinessDTO(Business business);
  public Business createBusinessDTOToBusiness(CreateBusinessDTO createBusinessDTO);
  public Business updateBusinessFromDto(UpdateBusinessDTO updateBusinessDTO, Business existingBusiness);
}
