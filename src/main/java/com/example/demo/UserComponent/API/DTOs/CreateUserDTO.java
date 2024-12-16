package com.example.demo.UserComponent.API.DTOs;

import java.util.UUID;

import com.example.demo.UserComponent.Domain.Entities.Enums.RoleType;

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
public class CreateUserDTO {

  @NotNull
  private final String username;

  @NotNull
  private final String password;

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
