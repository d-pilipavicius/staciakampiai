package com.example.demo.reservationComponent.api.dtos;

import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReservationsDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<ReservationDTO> items;

    public GetReservationsDTO(List<ReservationDTO> items, int totalItems) {
        this.items = items;
        this.totalItems = totalItems;
    }
}
