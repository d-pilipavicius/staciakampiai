package com.example.demo.BusinessComponent.API.DTOs;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetBusinessListDTO {

  private final Long totalItems;

  private final int totalPages;

  private final int currentPage;
  
  @NotNull
  private final List<@Valid BusinessDTO> items;
}
