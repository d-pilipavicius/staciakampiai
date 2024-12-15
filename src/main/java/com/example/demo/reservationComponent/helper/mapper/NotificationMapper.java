package com.example.demo.reservationComponent.helper.mapper;

import com.example.demo.CommonHelper.mapper.base.StaticMapper;
import com.example.demo.reservationComponent.api.dtos.reservationNotificationDTOs.ReservationNotificationDTO;
import com.example.demo.reservationComponent.domain.entities.ReservationNotification;

public class NotificationMapper {

    public static final StaticMapper<ReservationNotificationDTO, ReservationNotification> TO_MODEL = dto -> ReservationNotification.builder()
            .reservationId(dto.getReservationId())
            .text(dto.getText())
            .sentAt(dto.getSentAt())
            .build();

    public static final StaticMapper<ReservationNotification, ReservationNotificationDTO> TO_DTO = entity -> ReservationNotificationDTO.builder()
            .reservationId(entity.getReservationId())
            .text(entity.getText())
            .sentAt(entity.getSentAt())
            .build();
}
