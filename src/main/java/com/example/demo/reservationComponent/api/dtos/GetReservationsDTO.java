package com.example.demo.reservationComponent.api.dtos;

import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class GetReservationsDTO {
    private final int totalItems;
    private final int totalPages;
    private final int currentPage;
    private final List<ReservationDTO> items;
}
