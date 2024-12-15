package com.example.demo.reservationComponent.domain.services;

import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.notifications.event.ReservationCreatedEvent;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.reservationComponent.api.dtos.reservationNotificationDTOs.ReservationNotificationDTO;
import com.example.demo.reservationComponent.domain.entities.ReservationNotification;
import com.example.demo.reservationComponent.helper.factory.NotificationFactory;
import com.example.demo.reservationComponent.helper.mapper.NotificationMapper;
import com.example.demo.reservationComponent.repository.ReservationNotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class NotificationService {

    private final ReservationNotificationRepository reservationNotificationRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void sendReservationCreatedNotification(ReservationDTO reservationDto) {
        // Create event for notification
        ReservationCreatedEvent event = NotificationFactory.createReservationCreatedEvent(
                reservationDto.getId(),
                reservationDto.getCustomer().getPhoneNumber(),
                createNotificationText(reservationDto.getReservationStartAt())
        );

        // Publish event
        eventPublisher.publishEvent(event);
    }

    public String createNotificationText(Timestamp reservationStartAt) {
        return String.format("Your reservation is scheduled for %s-%s %s:%s. ", reservationStartAt.getMonth(), reservationStartAt.getDate(), reservationStartAt.getHours(), reservationStartAt.getMinutes());
    }

    public void createNotification(ReservationNotificationDTO notificationDTO) {
        ReservationNotification notification = Mapper.mapToModel(notificationDTO, NotificationMapper.TO_MODEL);
        reservationNotificationRepository.save(notification);
    }
}

