package com.example.demo.CommonDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageinationDTO{

  private final int page;

  private final int pageSize;
}
