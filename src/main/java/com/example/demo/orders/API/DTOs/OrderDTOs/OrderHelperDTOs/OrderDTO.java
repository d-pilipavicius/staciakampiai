package com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs;

import com.example.demo.orders.domain.entities.enums.Currency;
import com.example.demo.orders.domain.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UUID id;
    private UUID employeeId;
    private Optional<UUID> reservationId;
    private OrderStatus status;
    private Timestamp createdAt;
    private Optional<Timestamp> closedAt;
    private BigDecimal originalPrice;
    private BigDecimal discountsTotal;
    private BigDecimal subtotalPrice;
    private BigDecimal taxTotal;
    private BigDecimal serviceChargesTotal;
    private BigDecimal finalPrice;
    private Currency currency;
    private List<OrderItemDTO> items;
    private List<AppliedDiscountDTO> discounts;
    private List<AppliedServiceChargeDTO> serviceCharges;
    private UUID businessId;
}
