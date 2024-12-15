package com.example.demo.notifications.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class ReservationCreatedEvent {
    private final UUID reservationId;
    private final String phoneNumber;
    private final String text;
}
