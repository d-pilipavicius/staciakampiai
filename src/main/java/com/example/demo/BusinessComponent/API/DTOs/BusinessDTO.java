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
  private UUID id;
  @NotNull
  private String name;
  @NotNull
  private UUID ownerId;
  @NotNull
  private String address;
  @NotNull
  private String phoneNumber;
  @NotNull
  @Email
  private String emailAddress;
}
