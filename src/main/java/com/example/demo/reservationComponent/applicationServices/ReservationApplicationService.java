package com.example.demo.reservationComponent.applicationServices;

import com.example.demo.reservationComponent.api.dtos.GetReservationsDTO;
import com.example.demo.reservationComponent.api.dtos.PutReservationDTO;
import com.example.demo.reservationComponent.api.dtos.PostReservationDTO;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.FullNameDTO;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.reservationComponent.api.dtos.reservationNotificationDTOs.ReservationNotificationDTO;
import com.example.demo.reservationComponent.domain.services.NotificationService;
import com.example.demo.reservationComponent.domain.services.ReservationService;
import com.example.demo.reservationComponent.helper.factory.NotificationFactory;
import com.example.demo.reservationComponent.helper.validator.ReservationValidator;
import com.example.demo.serviceChargeComponent.applicationServices.ServiceChargeApplicationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReservationApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationApplicationService.class);

    private final ReservationService reservationService;
    private final ServiceChargeApplicationService serviceChargeApplicationService;
    private final ReservationValidator reservationValidator;
    private final NotificationService notificationService;

    @Transactional
    public ReservationDTO createReservation(PostReservationDTO postReservationDTO, UUID employeeId) {
        // Validate dto
        reservationValidator.validatePostReservationDTO(postReservationDTO);

        // Validate service charge ids
        serviceChargeApplicationService.validateServiceChargeIds(postReservationDTO.getServiceChargeIds());

        // Create reservation
        ReservationDTO reservationDto = reservationService.createReservation(postReservationDTO, employeeId);

        // Create notification dto
        ReservationNotificationDTO notificationDTO = NotificationFactory.createNotificationDTO(
                notificationService.createNotificationText(reservationDto.getReservationStartAt()),
                new Timestamp(System.currentTimeMillis())
        );

        // todo: Send notification
        // notificationService.sendReservationCreatedNotification(reservationDto);

        // Save notification
        notificationService.createNotification(notificationDTO);

        // Create reservation
        return reservationDto;
    }

    public GetReservationsDTO getActiveReservationsByFullName(FullNameDTO fullNameDTO, int page, int size) {
        return reservationService.getActiveReservationsByFullName(fullNameDTO, page, size);
    }

    public GetReservationsDTO getReservationsByBusinessId(UUID businessId, int page, int size) {
        return reservationService.getReservationsByBusinessId(businessId, page, size);
    }

    @Transactional
    public ReservationDTO updateReservation(PutReservationDTO putReservationDTO, UUID reservationId) {
        // Update reservation
        ReservationDTO reservationDTO = reservationService.updateReservation(putReservationDTO, reservationId);

        // Create notification dto
        ReservationNotificationDTO notificationDTO = NotificationFactory.createNotificationDTO(
                notificationService.createNotificationText(reservationDTO.getReservationStartAt()),
                new Timestamp(System.currentTimeMillis())
        );

        // todo: Send notification
        // notificationService.sendReservationCreatedNotification(reservationDTO);

        // Save notification
        notificationService.createNotification(notificationDTO);

        return reservationDTO;
    }

    @Transactional
    public void deleteReservation(UUID id) {
        reservationService.deleteReservation(id);
    }
}
