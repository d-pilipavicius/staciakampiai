package com.example.demo.payments.API.DTOs;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.example.demo.CommonHelper.enums.Currency;

@Getter
@Builder
@AllArgsConstructor
public class AddTipDTO {
    @NotNull
    private UUID orderId;

    @NotNull
    private UUID employeeId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Currency currency;
}
