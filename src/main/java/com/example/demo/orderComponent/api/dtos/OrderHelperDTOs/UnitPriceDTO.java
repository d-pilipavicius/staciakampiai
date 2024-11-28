package com.example.demo.orderComponent.api.dtos.OrderHelperDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnitPriceDTO {
    private BigDecimal base;
    private BigDecimal withModifiers;
}
