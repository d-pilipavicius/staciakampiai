package com.example.demo.BusinessComponent.Helpers.Mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.demo.BusinessComponent.API.DTOs.CreateUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UpdateUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UserDTO;
import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.BusinessComponent.Domain.Entities.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Component
public class UserMapper {
  
  public User toUser(@NotNull @Valid CreateUserDTO createUserDTO) {
    //If businessId was passed as null, business is null, else pass business; 
    Business business = (createUserDTO.getBusinessId() == null) ? null : Business.builder().id(createUserDTO.getBusinessId()).build();
    return User
      .builder()
      .fullName(createUserDTO.getFullName())
      .phoneNumber(createUserDTO.getPhoneNumber())
      .emailAddress(createUserDTO.getEmailAddress())
      .business(business)
      .role(createUserDTO.getRole())
      .build();
  }
  public User toUser(@NotNull @Valid UpdateUserDTO updateUserDTO, @NotNull UUID userId) {
    //If businessId was passed as null, business is null, else pass business; 
    return User
      .builder()
      .id(userId)
      .fullName(updateUserDTO.getFullName())
      .phoneNumber(updateUserDTO.getPhoneNumber())
      .emailAddress(updateUserDTO.getEmailAddress())
      .role(updateUserDTO.getRole())
      .build();
  }

  public UserDTO toUserDTO(@NotNull @Valid User user) {
    UUID businessId = (user.getBusiness() == null) ? null : user.getBusiness().getId();
    return UserDTO
      .builder()
      .id(user.getId())
      .fullName(user.getFullName())
      .phoneNumber(user.getPhoneNumber())
      .emailAddress(user.getEmailAddress())
      .businessId(businessId)
      .role(user.getRole())
      .build();
  }
}
