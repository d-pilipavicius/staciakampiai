package com.example.demo.BusinessComponent.API.DTOs;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateBusinessDTO {

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
