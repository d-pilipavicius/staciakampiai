package com.example.demo.taxComponent.api.dtos.TaxHelperDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxDTO {
    private UUID id;
    private String title;
    private BigDecimal ratePercentage;
    private UUID businessId;
}
