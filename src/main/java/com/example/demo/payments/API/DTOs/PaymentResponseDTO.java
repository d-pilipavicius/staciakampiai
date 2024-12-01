package com.example.demo.payments.API.DTOs;

import com.example.demo.payments.domain.entities.enums.Currency;
import com.example.demo.payments.domain.entities.enums.PaymentMethod;
import com.example.demo.payments.domain.entities.enums.PaymentStatus;
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
    private UUID id;
    private UUID orderId;
    private List<OrderItemPaymentDTO> orderItems;
    private BigDecimal amount;
    private Currency currency;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
}

