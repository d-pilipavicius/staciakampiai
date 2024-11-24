package com.example.demo.BusinessSystem.Services.Interfaces;

import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.Entities.Business;

public interface IBusinessService {
  public Business createBusiness(CreateBusinessDTO dto);
}
