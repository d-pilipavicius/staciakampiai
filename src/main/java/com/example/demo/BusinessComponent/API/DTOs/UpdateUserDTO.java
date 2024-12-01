package com.example.demo.BusinessComponent.API.DTOs;

import com.example.demo.BusinessComponent.Domain.Entities.Enums.RoleType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserDTO {
  
  private final String fullName;

  @Pattern(regexp = "\\+\\d{3,30}")
  private final String phoneNumber;

  @Email
  private final String emailAddress;

  @Enumerated(EnumType.STRING)
  private final RoleType role;
}
