package com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxLine {
    private UUID id;
    private String title;
    private BigDecimal ratePercentage;
    private BigDecimal amount;
    private Currency currency;
}
