package com.example.demo.orders.api.DTOs.TaxDTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PostTaxDTO {
    private String title;
    private BigDecimal ratePercentage;
    private UUID businessId;
}
