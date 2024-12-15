package com.example.demo.BusinessComponent.API.DTOs;

import java.util.UUID;

import com.example.demo.BusinessComponent.Domain.Entities.Enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BusinessDTO {

  @NotNull
  private final UUID id;

  @NotNull
  private final String name;

  @NotNull
  private final UUID ownerId;

  @NotNull
  private final String address;

  @NotNull
  private final String phoneNumber;
  
  @NotNull
  @Email
  private final String emailAddress;
}
