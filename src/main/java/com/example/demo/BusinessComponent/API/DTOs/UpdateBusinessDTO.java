package com.example.demo.BusinessComponent.API.DTOs;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateBusinessDTO {

  private final String name;

  private final UUID ownerId;

  private final String address;

  private final String phoneNumber;

  @Email
  private final String emailAddress;
}
