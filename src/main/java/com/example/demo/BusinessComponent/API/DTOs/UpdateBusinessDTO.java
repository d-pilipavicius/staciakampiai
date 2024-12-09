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
  
  private String name;
  
  private UUID ownerId;
 
  private String address;

  private String phoneNumber;

  @Email
  private String emailAddress;
}
