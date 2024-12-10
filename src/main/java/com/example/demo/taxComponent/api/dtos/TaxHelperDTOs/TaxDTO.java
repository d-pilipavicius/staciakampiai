package com.example.demo.taxComponent.api.dtos.TaxHelperDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TaxDTO {
    private UUID id;
    private String title;
    private BigDecimal ratePercentage;
    private UUID businessId;
}
