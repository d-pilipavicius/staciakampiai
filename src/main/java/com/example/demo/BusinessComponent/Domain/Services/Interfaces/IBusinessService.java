package com.example.demo.BusinessComponent.Domain.Services.Interfaces;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;

public interface IBusinessService {
  public BusinessDTO createBusiness(CreateBusinessDTO dto);
}
