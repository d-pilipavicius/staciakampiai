package com.example.demo.OrderComponent.API.DTOs;


import com.example.demo.helper.enums.Currency;
import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class OrderDTO {
    private UUID id;
    @NonNull
    private UUID employeeId;
    @NonNull
    private UUID reservationId;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private BigDecimal originalPrice;
    private Currency currency;
    @NonNull
    private List<OrderItemDTO> items;
    @NonNull
    private UUID businessId;
}
