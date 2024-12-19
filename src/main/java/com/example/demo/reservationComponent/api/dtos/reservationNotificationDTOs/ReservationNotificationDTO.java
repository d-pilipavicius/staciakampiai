package com.example.demo.reservationComponent.api.dtos.reservationNotificationDTOs;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
public class ReservationNotificationDTO {
    private final UUID reservationId;
    private final String text;
    private final Timestamp sentAt;
}
