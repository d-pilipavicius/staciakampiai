package com.example.demo.OrderComponent.API.DTOs;


import com.example.demo.helper.enums.Currency;
import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private UUID id;
    private UUID employeeId;
    private UUID reservationId;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private BigDecimal originalPrice;
    private Currency currency;
    private List<OrderItemDTO> items;
    private UUID businessId;
}