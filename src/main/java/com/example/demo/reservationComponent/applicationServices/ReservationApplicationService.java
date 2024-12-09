package com.example.demo.reservationComponent.applicationServices;

import com.example.demo.reservationComponent.api.dtos.GetReservationsDTO;
import com.example.demo.reservationComponent.api.dtos.PatchReservationDTO;
import com.example.demo.reservationComponent.api.dtos.PostReservationDTO;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.reservationComponent.api.dtos.ResponseReservationDTO;
import com.example.demo.reservationComponent.domain.services.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ReservationApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationApplicationService.class);

    private final ReservationService reservationService;

    public ReservationApplicationService(
            ReservationService reservationService
    ) {
        this.reservationService = reservationService;
    }

    @Transactional
    public ReservationDTO createReservation(PostReservationDTO postReservationDTO) {
        return reservationService.createReservation(postReservationDTO);
    }

    public GetReservationsDTO getAllReservations() {
        return reservationService.getReservations();
    }

    public GetReservationsDTO getAllReservations(int page, int size) {
        return reservationService.getReservations(page, size);
    }

    @Transactional
    public ResponseReservationDTO updateReservation(PatchReservationDTO patchReservationDTO, UUID id) {
        return reservationService.updateReservation(patchReservationDTO, id);
    }

    @Transactional
    public void deleteReservation(UUID id) {
        reservationService.deleteReservation(id);
    }
}
