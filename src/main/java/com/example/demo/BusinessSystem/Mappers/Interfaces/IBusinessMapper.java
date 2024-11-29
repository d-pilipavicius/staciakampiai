package com.example.demo.BusinessSystem.Mappers.Interfaces;

import org.springframework.data.domain.Page;

import com.example.demo.BusinessSystem.DTOs.BusinessDTO;
import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.DTOs.GetBusinessListDTO;
import com.example.demo.BusinessSystem.Entities.Business;

public interface IBusinessMapper {
  public BusinessDTO businessToBusinessDTO(Business business);
  public Business createBusinessDTOToBusiness(CreateBusinessDTO createBusinessDTO);
  public GetBusinessListDTO businessPageToGetBusinessListDTO(Page<Business> page);
}
