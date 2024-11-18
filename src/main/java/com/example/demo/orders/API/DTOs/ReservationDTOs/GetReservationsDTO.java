package com.example.demo.orders.api.DTOs.ReservationDTOs;

import com.example.demo.orders.api.DTOs.ReservationDTOs.ReservationDTOsObjects.FullReservation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetReservationsDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<FullReservation> items;
}
