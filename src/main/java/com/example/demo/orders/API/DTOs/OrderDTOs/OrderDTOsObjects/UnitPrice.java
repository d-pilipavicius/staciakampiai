package com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnitPrice {
    private BigDecimal base;
    private BigDecimal withModifiers;
}
