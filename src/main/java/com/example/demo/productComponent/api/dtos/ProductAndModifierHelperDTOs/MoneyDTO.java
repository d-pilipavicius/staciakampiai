package com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs;

import com.example.demo.helper.enums.Currency;
import lombok.*;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDTO {
    @NonNull
    private BigDecimal amount;
    @NonNull
    private Currency currency;
}
