package com.example.demo.orders.api.DTOs.TaxDTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
public class PatchTaxDTO {
    private Optional<String> title;
    private Optional<BigDecimal> ratePercentage;
}
