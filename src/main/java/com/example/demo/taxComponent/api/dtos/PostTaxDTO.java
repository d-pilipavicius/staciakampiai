package com.example.demo.taxComponent.api.dtos;

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
public class PostTaxDTO {
    private String title;
    private BigDecimal ratePercentage;
    private UUID businessId;
}
