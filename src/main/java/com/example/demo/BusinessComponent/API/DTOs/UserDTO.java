package com.example.demo.BusinessComponent.API.DTOs;

import java.util.UUID;

import com.example.demo.BusinessComponent.Domain.Entities.Enums.RoleType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDTO {
  
  @NotNull
  private final UUID id;
  
  @NotNull
  private final String fullName;

  @NotNull 
  @Pattern(regexp = "\\+\\d{3,30}")
  private final String phoneNumber;

  @NotNull
  @Email
  private final String emailAddress;

  private final UUID businessId;

  @Enumerated(EnumType.STRING)
  @NotNull
  private final RoleType role;
}
