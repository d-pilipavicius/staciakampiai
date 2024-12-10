package com.example.demo.taxComponent.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PutTaxDTO {
    private final Optional<String> title;
    private final Optional<BigDecimal> ratePercentage;
}
