package com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs;

import com.example.demo.helper.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
public class MoneyDTO {
    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final Currency currency;
}
