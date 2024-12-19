package com.example.demo.reservationComponent.helper.factory;

import com.example.demo.notifications.event.ReservationCreatedEvent;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.reservationComponent.api.dtos.reservationNotificationDTOs.ReservationNotificationDTO;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Component
public class NotificationFactory {

    public static ReservationCreatedEvent createReservationCreatedEvent(UUID reservationId, String phoneNumber, String text) {
        return ReservationCreatedEvent.builder()
                .reservationId(reservationId)
                .phoneNumber(phoneNumber)
                .text(text)
                .build();
    }

    public static ReservationNotificationDTO createNotificationDTO(String text, Timestamp sentAt, ReservationDTO reservationDTO) {
        return ReservationNotificationDTO.builder()
                .reservationId(reservationDTO.getId())
                .text(text)
                .sentAt(sentAt)
                .build();
    }
}
