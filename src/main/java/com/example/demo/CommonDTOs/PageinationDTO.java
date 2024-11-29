package com.example.demo.CommonDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PageinationDTO{
  private int page;
  private int pageSize;
}
