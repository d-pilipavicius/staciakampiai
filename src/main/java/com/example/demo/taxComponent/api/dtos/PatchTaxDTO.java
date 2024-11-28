package com.example.demo.taxComponent.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchTaxDTO {
    private Optional<String> title;
    private Optional<BigDecimal> ratePercentage;
}
