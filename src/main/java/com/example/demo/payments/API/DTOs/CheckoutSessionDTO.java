package com.example.demo.payments.API.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CheckoutSessionDTO {
    private final UUID paymentId;
    private final String checkoutUrl;
}
