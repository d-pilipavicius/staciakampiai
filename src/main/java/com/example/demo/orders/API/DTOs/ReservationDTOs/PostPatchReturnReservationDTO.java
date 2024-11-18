package com.example.demo.orders.API.DTOs.ReservationDTOs;

import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects.FullReservation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPatchReturnReservationDTO {
    private FullReservation reservation;
}
