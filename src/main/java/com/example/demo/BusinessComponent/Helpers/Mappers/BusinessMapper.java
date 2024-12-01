package com.example.demo.BusinessComponent.Helpers.Mappers;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.demo.BusinessComponent.API.DTOs.BusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessComponent.API.DTOs.GetBusinessListDTO;
import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.BusinessComponent.Domain.Entities.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


@Component
public class BusinessMapper {

  public BusinessDTO toBusinessDTO(@NotNull @Valid Business business) {
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

  public Business toBusiness(@NotNull @Valid CreateBusinessDTO createBusinessDTO) {
    return Business
      .builder()
      .name(createBusinessDTO.getName())
      .owner(User.builder().id(createBusinessDTO.getOwnerId()).build())
      .address(createBusinessDTO.getAddress())
      .phoneNumber(createBusinessDTO.getPhoneNumber())
      .emailAddress(createBusinessDTO.getEmailAddress())
      .build();
  }

  public GetBusinessListDTO toGetBusinessListDTO(@NotNull @Valid Page<Business> page) { 
    return GetBusinessListDTO
      .builder()
      .currentPage(page.getNumber())
      .totalItems(page.getTotalElements())
      .totalPages(page.getTotalPages())
      //Get retrieved Business enitites list and map it to a BusinessDTO list
      .items(page.getContent().stream().map(this::toBusinessDTO).collect(Collectors.toList()))
      .build();
  }
}