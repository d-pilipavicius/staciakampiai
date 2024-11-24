package com.example.demo.BusinessSystem.DTOs;

import java.util.UUID;

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
