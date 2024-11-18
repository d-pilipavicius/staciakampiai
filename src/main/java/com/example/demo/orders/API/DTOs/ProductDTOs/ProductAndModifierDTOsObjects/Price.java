package com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects;

import com.example.demo.orders.domain.entities.enums.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Price {
    private BigDecimal amount;
    private Currency currency;
}
