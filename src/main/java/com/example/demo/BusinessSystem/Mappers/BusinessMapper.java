package com.example.demo.BusinessSystem.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.demo.BusinessSystem.DTOs.BusinessDTO;
import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.DTOs.GetBusinessListDTO;
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

  @Override
  public GetBusinessListDTO businessPageToGetBusinessListDTO(Page<Business> page) { 
    return GetBusinessListDTO
      .builder()
      .currentPage(page.getNumber())
      .totalItems(page.getTotalElements())
      .totalPages(page.getTotalPages())
      //Get retrieved Business enitites list and map it to a BusinessDTO list
      .items(page.getContent().stream().map(this::businessToBusinessDTO).collect(Collectors.toList()))
      .build();
  }
}