package com.example.demo.payments.API.DTOs;

import com.example.demo.CommonHelper.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CheckoutSessionCompletedDTO {
    @NotNull
    private String transactionId;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Currency currency;
}
