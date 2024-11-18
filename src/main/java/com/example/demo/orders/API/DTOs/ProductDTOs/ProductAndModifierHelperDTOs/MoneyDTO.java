package com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierHelperDTOs;

import com.example.demo.orders.domain.entities.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDTO {
    private BigDecimal amount;
    private Currency currency;
}
