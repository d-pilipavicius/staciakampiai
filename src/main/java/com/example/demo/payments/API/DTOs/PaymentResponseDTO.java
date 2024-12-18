package com.example.demo.payments.API.DTOs;

import com.example.demo.CommonHelper.enums.Currency;
import com.example.demo.payments.Domain.Entities.Enums.PaymentMethod;
import com.example.demo.payments.Domain.Entities.Enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponseDTO {
    private final UUID id;
    private final UUID orderId;
    private final List<OrderItemPaymentDTO> orderItems;
    private final BigDecimal amount;
    private final Currency currency;
    private final PaymentMethod paymentMethod;
    private final PaymentStatus paymentStatus;
    private final LocalDateTime createdAt;
}

