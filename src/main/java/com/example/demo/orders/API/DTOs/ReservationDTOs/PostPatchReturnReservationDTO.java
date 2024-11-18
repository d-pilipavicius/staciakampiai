package com.example.demo.orders.api.DTOs.ReservationDTOs;

import com.example.demo.orders.api.DTOs.ReservationDTOs.ReservationDTOsObjects.FullReservation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPatchReturnReservationDTO {
    private FullReservation reservation;
}
