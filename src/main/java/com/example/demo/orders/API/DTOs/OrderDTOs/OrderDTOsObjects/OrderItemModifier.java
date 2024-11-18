package com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Getter
@Setter
public class OrderItemModifier {
    private UUID id;
    private String title;
    private BigDecimal price;
    private Currency currency;

}
