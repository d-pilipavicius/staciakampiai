package com.example.demo.reservationComponent.applicationServices;

import com.example.demo.reservationComponent.api.dtos.GetReservationsDTO;
import com.example.demo.reservationComponent.api.dtos.PutReservationDTO;
import com.example.demo.reservationComponent.api.dtos.PostReservationDTO;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.reservationComponent.domain.services.ReservationService;
import com.example.demo.reservationComponent.helper.validator.ReservationValidator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ReservationApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationApplicationService.class);

    private final ReservationService reservationService;
    private final AppliedServiceChargeService appliedServiceChargeService;
    private final ReservationValidator reservationValidator;

    @Transactional
    public ReservationDTO createReservation(PostReservationDTO postReservationDTO, UUID employeeId) {
        // Validate dto
        reservationValidator.validatePostReservationDTO(postReservationDTO);

        // Create reservation
        ReservationDTO reservationDTO = reservationService.createReservation(postReservationDTO, employeeId);

        // Add reservation to all provided applied service charges
        appliedServiceChargeService.getAppliedServiceChargesByIds(postReservationDTO.getServiceChargeIds()).forEach(sc ->
                appliedServiceChargeService.addReservationToAppliedServiceCharge(sc, reservationDTO.getId())
        );

        return reservationDTO;
    }

    public GetReservationsDTO getReservationsByBusinessId(UUID businessId, int page, int size) {
        return reservationService.getReservationsByBusinessId(businessId, page, size);
    }

    @Transactional
    public ReservationDTO updateReservation(PutReservationDTO putReservationDTO, UUID reservationId) {
        return reservationService.updateReservation(putReservationDTO, reservationId);
    }

    @Transactional
    public void deleteReservation(UUID id) {
        reservationService.deleteReservation(id);
    }
}
