package com.example.demo.BusinessComponent.ApplicationServices;

import java.util.UUID;

import com.example.demo.UserComponent.ApplicationServices.UserApplicationService;
import org.springframework.stereotype.Service;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.GetBusinessListDTO;
import com.example.demo.BusinessComponent.API.DTOs.UpdateBusinessDTO;
import com.example.demo.BusinessComponent.Domain.Services.BusinessService;
import com.example.demo.CommonDTOs.PageinationDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BusinessApplicationService {
    private final BusinessService businessService;
    private final UserApplicationService userApplicationService;

    public BusinessDTO createBusiness(@NotNull @Valid CreateBusinessDTO createBusinessDTO) {
        userApplicationService.checkIfUserExists(createBusinessDTO.getOwnerId());
        return businessService.createBusiness(createBusinessDTO);
    }

    public BusinessDTO getBusiness(@NotNull UUID businessId) {
        return businessService.getBusiness(businessId);
    }

    public GetBusinessListDTO getBusinessList(@NotNull @Valid PageinationDTO pageinationDTO) {
        return businessService.getBusinessList(pageinationDTO);
    }

    public BusinessDTO updateBusiness(@NotNull UUID businessId, @NotNull @Valid UpdateBusinessDTO updateBusinessDTO) {
        if(updateBusinessDTO.getOwnerId() != null){
            userApplicationService.checkIfUserExists(updateBusinessDTO.getOwnerId());
        }
        return businessService.updateBusiness(businessId, updateBusinessDTO);
    }

    public void deleteBusiness(@NotNull UUID businessId) {
        businessService.deleteBusiness(businessId);
    }

}
