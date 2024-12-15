package com.example.demo.CommonHelper;

import org.springframework.stereotype.Component;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.ApplicationServices.BusinessApplicationService;
import com.example.demo.UserComponent.API.DTOs.CreateUserDTO;
import com.example.demo.UserComponent.API.DTOs.UpdateUserDTO;
import com.example.demo.UserComponent.API.DTOs.UserDTO;
import com.example.demo.UserComponent.ApplicationServices.UserApplicationService;
import com.example.demo.UserComponent.Domain.Entities.Enums.RoleType;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PostStartupConfig {
  private final UserApplicationService userService;
  private final BusinessApplicationService businessService;

  @PostConstruct
  public void databaseInit() {
    UserDTO newUser = userService.createUser(CreateUserDTO.builder()
        .fullName("John Doe")
        .emailAddress("example@example.com")
        .phoneNumber("+37061122334")
        .role(RoleType.BusinessOwner)
        .build());
    BusinessDTO newBusiness = businessService.createBusiness(CreateBusinessDTO.builder()
        .name("First Business Inc.")
        .ownerId(newUser.getId())
        .phoneNumber(newUser.getPhoneNumber())
        .emailAddress(newUser.getEmailAddress())
        .address("Address Avenue 123")
        .build());
    userService.updateUser(newUser.getId(), UpdateUserDTO.builder()
        .fullName(newUser.getFullName())
        .businessId(newBusiness.getId())
        .phoneNumber(newUser.getPhoneNumber())
        .emailAddress(newUser.getEmailAddress())
        .role(newUser.getRole())
        .build());
    System.out.println("User id: " + newUser.getId());
    System.out.println("Business id: " + newBusiness.getId());
  }
}
