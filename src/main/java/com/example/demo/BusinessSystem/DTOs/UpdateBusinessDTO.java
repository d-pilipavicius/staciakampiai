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
public class UpdateBusinessDTO {

  @NotNull
  private UUID businessId; 
  
  private String name;
  
  private UUID ownerId;
 
  private String address;

  private String phoneNumber;

  @Email
  private String emailAddress;
}
