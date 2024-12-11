package com.example.demo.OrderComponent.API.DTOs;

import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ModifyOrderRequest {
    private UUID reservationId;
    private OrderStatus status;
    private List<OrderItemRequest> items;
}

