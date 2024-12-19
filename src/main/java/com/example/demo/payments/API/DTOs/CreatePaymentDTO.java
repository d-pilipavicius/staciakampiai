package com.example.demo.payments.API.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.example.demo.CommonHelper.enums.Currency;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreatePaymentDTO {
    @NotNull
    private UUID orderId;
    @NotNull
    private UUID businessId;
    @NotNull
    private UUID employeeId;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Currency currency;
    @NotNull
    private List<OrderItemPaymentDTO> orderItems;
}

