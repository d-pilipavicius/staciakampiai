package com.example.demo.orderComponent.api.dtos.OrderHelperDTOs;

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
public class OrderItemDTO {
    private UUID id;
    private UUID productId;
    private String title;
    private int quantity;
    private UnitPriceDTO unitPrice;
    private BigDecimal originalPrice;
    private BigDecimal discountsTotal;
    private BigDecimal subtotalPrice;
    private BigDecimal taxTotal;
    private BigDecimal finalPrice;
    private Currency currency;
    private List<OrderItemModifierDTO> modifiers;
    private List<TaxLineDTO> taxLines;

}
