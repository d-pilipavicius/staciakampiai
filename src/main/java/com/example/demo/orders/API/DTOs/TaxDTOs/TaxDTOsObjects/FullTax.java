package com.example.demo.orders.API.DTOs.TaxDTOs.TaxDTOsObjects;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class FullTax {
    private UUID id;
    private String title;
    private BigDecimal ratePercentage;
    private UUID businessId;
}
