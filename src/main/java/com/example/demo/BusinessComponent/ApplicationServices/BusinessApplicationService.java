package com.example.demo.BusinessComponent.ApplicationServices;

import com.example.demo.BusinessComponent.API.DTOs.PostUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UserDTO;
import com.example.demo.BusinessComponent.Domain.Services.UserService;
import org.springframework.stereotype.Service;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.Domain.Services.BusinessService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BusinessApplicationService {
    private final BusinessService businessService;
    private final UserService userService;

    public BusinessDTO createBusiness(CreateBusinessDTO createBusinessDTO) {
        return businessService.createBusiness(createBusinessDTO);
    }

    public UserDTO createUser(PostUserDTO postUserDTO){
        return userService.createUser(postUserDTO);
    }
}
