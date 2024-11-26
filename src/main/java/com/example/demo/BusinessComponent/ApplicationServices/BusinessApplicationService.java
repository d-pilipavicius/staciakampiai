package com.example.demo.BusinessComponent.ApplicationServices;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.Domain.Services.Interfaces.IBusinessService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BusinessApplicationService {
    private final IBusinessService service;

    public BusinessDTO createBusiness(CreateBusinessDTO createBusinessDTO) {
        return service.createBusiness(createBusinessDTO);
    }
}
