package com.example.demo.BusinessSystem.Services.Interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.Entities.Business;
import com.example.demo.CommonDTOs.PageinationDTO;

public interface IBusinessService {
  public Business createBusiness(CreateBusinessDTO dto);
  public Page<Business> getBusinessList(PageinationDTO pageinationInfo);
  public Business getBusiness(UUID businessId);
}
