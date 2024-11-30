package com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs;

import com.example.demo.helper.enums.Currency;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDTO {
    private BigDecimal amount;
    private Currency currency;
}
