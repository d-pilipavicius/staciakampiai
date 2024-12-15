package com.example.demo.BusinessComponent.ApplicationServices;

<<<<<<< HEAD
import com.example.demo.BusinessComponent.API.DTOs.PostUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UserDTO;
import com.example.demo.BusinessComponent.Domain.Services.UserService;
=======
import java.util.UUID;

>>>>>>> e953f891b51777082b9fa4db964c3ea718ef8a2c
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
    private final UserService userService;

    public BusinessDTO createBusiness(@NotNull @Valid CreateBusinessDTO createBusinessDTO) {
        return businessService.createBusiness(createBusinessDTO);
    }

<<<<<<< HEAD
    public UserDTO createUser(PostUserDTO postUserDTO){
        return userService.createUser(postUserDTO);
    }
=======
    public BusinessDTO getBusiness(@NotNull UUID businessId) {
        return businessService.getBusiness(businessId);
    }

    public GetBusinessListDTO getBusinessList(@NotNull @Valid PageinationDTO pageinationDTO) {
        return businessService.getBusinessList(pageinationDTO);
    }

    public BusinessDTO updateBusiness(@NotNull UUID businessId, @NotNull @Valid UpdateBusinessDTO updateBusinessDTO) {
        return businessService.updateBusiness(businessId, updateBusinessDTO);
    }

    public void deleteBusiness(@NotNull UUID businessId) {
        businessService.deleteBusiness(businessId);
    }

>>>>>>> e953f891b51777082b9fa4db964c3ea718ef8a2c
}
