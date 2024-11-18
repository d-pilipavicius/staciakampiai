package com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UnitPrice {
    private BigDecimal base;
    private BigDecimal withModifiers;
}
