package com.example.demo.UserComponent.Helpers.Mappers;

import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.UserComponent.API.DTOs.CreateUserDTO;
import com.example.demo.UserComponent.API.DTOs.UpdateUserDTO;
import com.example.demo.UserComponent.API.DTOs.UserDTO;
import com.example.demo.UserComponent.Domain.Entities.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class UserMapper {

  private final PasswordEncoder passwordEncoder;

  public User toUser(@NotNull @Valid CreateUserDTO createUserDTO) {
    // If businessId was passed as null, business is null, else pass business;
    Business business = (createUserDTO.getBusinessId() == null) ? null
        : Business.builder().id(createUserDTO.getBusinessId()).build();
    return User
        .builder()
        .username(createUserDTO.getUsername())
        .password(passwordEncoder.encode(createUserDTO.getPassword()))
        .fullName(createUserDTO.getFullName())
        .phoneNumber(createUserDTO.getPhoneNumber())
        .emailAddress(createUserDTO.getEmailAddress())
        .business(business)
        .role(createUserDTO.getRole())
        .build();
  }

  public User toUser(@NotNull @Valid UpdateUserDTO updateUserDTO, @NotNull UUID userId) {
    // If businessId was passed as null, business is null, else pass business;
    Business business = (updateUserDTO.getBusinessId() == null) ? null
        : Business.builder().id(updateUserDTO.getBusinessId()).build();
    return User
        .builder()
        .id(userId)
        .fullName(updateUserDTO.getFullName())
        .phoneNumber(updateUserDTO.getPhoneNumber())
        .emailAddress(updateUserDTO.getEmailAddress())
        .business(business)
        .role(updateUserDTO.getRole())
        .build();
  }

  public UserDTO toUserDTO(@NotNull @Valid User user) {
    UUID businessId = (user.getBusiness() == null) ? null : user.getBusiness().getId();
    return UserDTO
        .builder()
        .id(user.getId())
        .username(user.getUsername())
        .fullName(user.getFullName())
        .phoneNumber(user.getPhoneNumber())
        .emailAddress(user.getEmailAddress())
        .businessId(businessId)
        .role(user.getRole())
        .build();
  }
}
