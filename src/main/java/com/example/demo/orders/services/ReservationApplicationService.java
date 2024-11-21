package com.example.demo.orders.services;

import com.example.demo.orders.API.DTOs.ReservationDTOs.PostReservationDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.orders.domain.services.DiscountService;
import com.example.demo.orders.domain.services.ProductService;
import com.example.demo.orders.domain.services.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReservationApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountApplicationService.class);

    private final ReservationService reservationService;

    public ReservationApplicationService(
            ReservationService reservationService
    ) {
        this.reservationService = reservationService;
    }

    public ReservationDTO createReservation(PostReservationDTO postReservationDTO) {
        return reservationService.createReservation(postReservationDTO);
    }
}
