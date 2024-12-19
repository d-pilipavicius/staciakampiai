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
public class TipDTO {
    @NotNull
    private UUID id;
    @NotNull
    private UUID orderId;
    @NotNull
    private UUID employeeId;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Currency currency;
    @NotNull
    private UUID businessId;

}

