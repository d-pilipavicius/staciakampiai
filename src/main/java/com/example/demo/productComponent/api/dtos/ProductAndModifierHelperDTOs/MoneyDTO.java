package com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs;

import com.example.demo.helper.enums.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
public class MoneyDTO {
    @NotNull(message = "Amount is required")
    @PositiveOrZero(message = "Amount must be greater than or equal to 0")
    private final BigDecimal amount;

    @NotNull(message = "Currency is required")
    private final Currency currency;
}