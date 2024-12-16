package com.example.demo.OrderComponent.API.DTOs;

import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NonNull
@Builder
public class ModifyOrderDTO {
    private final UUID reservationId;
    private final OrderStatus status;
    private final List<OrderItemDTO> items;
    private final List<UUID> serviceChargeIds;
    private List<AppliedServiceChargeDTO> serviceCharges;
}

