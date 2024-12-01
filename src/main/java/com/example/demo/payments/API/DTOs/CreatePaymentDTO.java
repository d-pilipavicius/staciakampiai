package com.example.demo.payments.API.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.example.demo.payments.Domain.Entities.Enums.Currency;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreatePaymentDTO {
    @NotNull
    private UUID orderId;
    @NotNull
    private UUID businessId;
    @NotNull
    private UUID employeeId;
    private BigDecimal amount;
    @NotNull
    private Currency currency;
    @NotNull
    private List<OrderItemPaymentDTO> orderItems;
}

