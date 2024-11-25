package com.example.demo.BusinessSystem.Services.Interfaces;

import java.util.UUID;

import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.DTOs.UpdateBusinessDTO;
import com.example.demo.BusinessSystem.Entities.Business;

public interface IBusinessService {
  public Business createBusiness(CreateBusinessDTO dto);

  public void deleteBusiness(UUID businessId);

  public Business updateBusiness(UUID businessId, UpdateBusinessDTO dto);
  
}
