package com.example.demo.orders.services;

import com.example.demo.orders.API.DTOs.ReservationDTOs.GetReservationsDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.PatchReservationDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.PostReservationDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.ResponseReservationDTO;
import com.example.demo.orders.domain.services.ReservationService;
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
