package com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects;

import com.example.demo.orders.domain.entities.enums.Currency;
import com.example.demo.orders.domain.entities.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class FullOrder {
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
    private List<OrderItem> items;
    private List<AppliedDiscount> discounts;
    private List<ServiceChargeInOrder> serviceCharges;
    private UUID businessId;


}
