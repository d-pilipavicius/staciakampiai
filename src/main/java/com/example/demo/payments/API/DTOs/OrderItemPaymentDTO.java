package com.example.demo.payments.API.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderItemPaymentDTO {
    @NotNull
    private UUID orderItemId;
    @NotNull
    private Integer quantity;
}