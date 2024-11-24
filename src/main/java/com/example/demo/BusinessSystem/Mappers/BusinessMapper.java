package com.example.demo.BusinessSystem.Mappers;

import org.springframework.stereotype.Component;

import com.example.demo.BusinessSystem.DTOs.BusinessDTO;
import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.Entities.Business;
import com.example.demo.BusinessSystem.Entities.User;
import com.example.demo.BusinessSystem.Mappers.Interfaces.IBusinessMapper;

@Component
public class BusinessMapper implements IBusinessMapper {

  @Override
  public BusinessDTO businessToBusinessDTO(Business business) {
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

  @Override
  public Business createBusinessDTOToBusiness(CreateBusinessDTO createBusinessDTO) {
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