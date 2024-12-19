package com.example.demo.CommonHelper;

import com.example.demo.UserComponent.API.DTOs.PutUserCredentialsDTO;
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
    UserDTO newBusinessOwner = userService.createUser(CreateUserDTO.builder()
        .fullName("John Doe")
        .emailAddress("example@example.com")
        .phoneNumber("+37061122334")
        .role(RoleType.BusinessOwner)
        .username("Tomas")
        .password("plumbum")
        .build());
    BusinessDTO newBusiness = businessService.createBusiness(CreateBusinessDTO.builder()
        .name("First Business Inc.")
        .ownerId(newBusinessOwner.getId())
        .phoneNumber(newBusinessOwner.getPhoneNumber())
        .emailAddress(newBusinessOwner.getEmailAddress())
        .address("Address Avenue 123")
        .build());
    userService.updateSensitiveInformation(newBusinessOwner.getId(), PutUserCredentialsDTO.builder()
        .businessId(newBusiness.getId())
        .username("Tomas")
        .password("plumbum")
        .role(newBusinessOwner.getRole())
        .build());
    UserDTO itAdministrator = userService.createUser(CreateUserDTO.builder()
        .fullName("Dzeimsas Bondas")
        .emailAddress("example@example.com")
        .phoneNumber("+37061122334")
        .role(RoleType.ITAdministrator)
        .username("Lapas")
        .password("traukinys")
        .build());
    UserDTO employee = userService.createUser(CreateUserDTO.builder()
            .fullName("Raganosis penkiolika")
            .emailAddress("example@example.com")
            .phoneNumber("+37061122334")
            .role(RoleType.Employee)
            .username("karatistas5000")
            .businessId(newBusiness.getId())
            .password("traktorius")
            .build());
    UserDTO ownerOfSecond = userService.createUser(CreateUserDTO.builder()
            .fullName("Raganosis penkiolika")
            .emailAddress("example@example.com")
            .phoneNumber("+37061122334")
            .role(RoleType.Employee)
            .username("naujas")
            .password("traktorius")
            .build());
    BusinessDTO businessWithoutEmployee = businessService.createBusiness(CreateBusinessDTO.builder()
            .name("First Business Inc.")
            .ownerId(ownerOfSecond.getId())
            .phoneNumber(ownerOfSecond.getPhoneNumber())
            .emailAddress(ownerOfSecond.getEmailAddress())
            .address("Address Avenue 123")
            .build());
    userService.updateSensitiveInformation(ownerOfSecond.getId(), PutUserCredentialsDTO.builder()
            .role(ownerOfSecond.getRole())
            .businessId(businessWithoutEmployee.getId())
            .password("traktorius")
            .username("naujas")
            .build());
    System.out.println("UserBusinessOwner id: " + newBusinessOwner.getId() + ", username: " + newBusinessOwner.getUsername() + " password: plumbum");
    System.out.println("Business, which has employee, id: " + newBusiness.getId());
    System.out.println("ITADMIN id: " + itAdministrator.getId() + ", username: " + itAdministrator.getUsername() + " password: traukinys");
    System.out.println("employee id: " + employee.getId() + ", username: " + employee.getUsername() + " password: traktorius");
    System.out.println("Business, which doesnt have employee, id:" + businessWithoutEmployee.getId());
    System.out.println("Owner of business without employee id: " + ownerOfSecond.getId() + ", username: " + newBusinessOwner.getUsername() + " password: traktorius");
  }
}
