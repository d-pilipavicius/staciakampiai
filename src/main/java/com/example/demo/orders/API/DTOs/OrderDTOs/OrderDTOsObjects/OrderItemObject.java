package com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemObject {
    private UUID id;
    private UUID productId;
    private String title;
    private int quantity;
    private UnitPrice unitPrice;
    private BigDecimal originalPrice;
    private BigDecimal discountsTotal;
    private BigDecimal subtotalPrice;
    private BigDecimal taxTotal;
    private BigDecimal finalPrice;
    private Currency currency;
    private List<OrderItemModifierObject> modifiers;
    private List<TaxLine> taxLines;

}
