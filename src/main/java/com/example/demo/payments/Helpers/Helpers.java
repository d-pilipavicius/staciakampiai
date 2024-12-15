package com.example.demo.payments.Helpers;

import com.example.demo.payments.API.DTOs.OrderItemPaymentDTO;

import java.math.BigDecimal;
import java.util.List;

public class Helpers {
    public static BigDecimal calculateTotalAmount(List<OrderItemPaymentDTO> orderItems) {
        return orderItems.stream()
                .map(item -> item.getAmount().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
