package com.example.demo.BusinessComponent.ApplicationServices;

import org.springframework.stereotype.Service;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.Domain.Services.BusinessService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BusinessApplicationService {
    private final BusinessService businessService;

    public BusinessDTO createBusiness(CreateBusinessDTO createBusinessDTO) {
        return businessService.createBusiness(createBusinessDTO);
    }
}
