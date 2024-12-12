package com.example.demo.OrderComponent.API.DTOs;

import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class ModifyOrderDTO {
    private UUID reservationId;
    private OrderStatus status;
    private List<OrderItemDTO> items;
}

