package com.example.demo.BusinessComponent.API.DTOs;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
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
