package com.example.demo.taxComponent.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PostTaxDTO {
    private String title;
    private BigDecimal ratePercentage;
    private UUID businessId;
}
