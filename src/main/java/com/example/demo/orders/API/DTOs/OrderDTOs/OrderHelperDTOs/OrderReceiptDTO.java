package com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs;

import com.example.demo.orders.domain.entities.AppliedServiceCharge;
import com.example.demo.orders.domain.entities.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReceiptDTO {
    private UUID orderId;
    private Timestamp createdAt;
    private BigDecimal originalPrice;
    private BigDecimal discountsTotal;
    private BigDecimal subtotalPrice;
    private BigDecimal taxTotal;
    private BigDecimal serviceChargesTotal;
    private BigDecimal finalPrice;
    private Currency currency;
    private List<OrderItemDTO> items;
    private List<AppliedDiscountDTO> discounts;
    private List<AppliedServiceCharge> serviceCharges;
}