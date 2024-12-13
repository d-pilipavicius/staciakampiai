package com.example.demo.BusinessComponent.Helpers.Mappers;

import com.example.demo.BusinessComponent.API.DTOs.PostUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.BusinessComponent.Domain.Entities.User;

@Component
@AllArgsConstructor
public class BusinessMapper {
  private final PasswordEncoder passwordEncoder;

  public BusinessDTO toBusinessDTO(Business business) {
    return BusinessDTO
      .builder()
      .id(business.getId())
      .name(business.getName())
      .ownerId(business.getOwner().getId())
      .address(business.getAddress())
      .phoneNumber(business.getPhoneNumber())
      .emailAddress(business.getEmailAddress())
      .build();
  }

  public Business toBusiness(CreateBusinessDTO createBusinessDTO) {
    return Business
      .builder()
      .name(createBusinessDTO.getName())
      .owner(User.builder().id(createBusinessDTO.getOwnerId()).build())
      .address(createBusinessDTO.getAddress())
      .phoneNumber(createBusinessDTO.getPhoneNumber())
      .emailAddress(createBusinessDTO.getEmailAddress())
      .build();
  }
}