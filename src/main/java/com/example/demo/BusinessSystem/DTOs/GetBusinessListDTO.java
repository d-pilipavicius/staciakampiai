package com.example.demo.BusinessSystem.DTOs;

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
  private Long totalItems;
  private int totalPages;
  private int currentPage;
  @NotNull
  private List<@Valid BusinessDTO> items;
}
